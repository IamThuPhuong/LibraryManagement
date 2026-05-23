package main.service;

import main.constants.Constants;
import main.enums.BorrowStatus;
import main.enums.Permission;
import main.entity.BorrowCard;
import main.entity.BorrowDetail;
import main.entity.User;
import main.repository.BookRepository;
import main.repository.BorrowDetailRepository;
import main.repository.BorrowRepository;
import main.validate.AuthorValidator;
import main.validate.BorrowValidator;
import main.validate.Validator;
import main.vo.BorrowVO;

import java.util.ArrayList;
import java.util.List;

import static test.MainMenuTest.userRepository;

public class BorrowService {
    private final Validator<BorrowVO> borrowValidator = new BorrowValidator();
    private final AuthorService authorService = new AuthorService();
    private final User currentUser = userRepository.findByUserId(AuthenService.USER_ID);
    Validator<Permission> authorValidator = new AuthorValidator(currentUser, authorService);
    private final BorrowRepository borrowRepository = new BorrowRepository();
    private final BorrowDetailRepository borrowDetailRepository = new BorrowDetailRepository();
    private final BookRepository bookRepository = new BookRepository();
    /** Quyền truy cập chức năng */
    private static final Permission PERMISSION_OF_FUNCTION = Permission.MANAGE_USER;

    /**
     * 4. Lập phiếu mượn sách
     * @param vo
     * @return
     */
    public BorrowCard setBorrowCard(BorrowVO vo){

        authorValidator.validate(PERMISSION_OF_FUNCTION);

        List<String> errorList = borrowValidator.validate(vo);
        if (!errorList.isEmpty()){
            System.out.println("Không thể lập thẻ mượn sách do các lỗi sau:");
            for (String error : errorList) {
                System.out.println("- " + error);
            }
            return null;
        }

        BorrowCard borrowCard = new BorrowCard();
        borrowCard.setBorrowId(vo.getBorrowId());
        borrowCard.setReaderId(vo.getReaderId());
        if (vo.getBorrowDate() == null){
            borrowCard.setBorrowDate(Constants.TODAY);
        } else {
            borrowCard.setBorrowDate(vo.getBorrowDate());
        }
        borrowCard.setDueDate(vo.getBorrowDate().plusMonths(1));

        List<BorrowDetail> listBorrowDetail = new ArrayList<>();
        for (int i = 0; i < vo.getAmount(); i++) {
            BorrowDetail borrowDetail = new BorrowDetail();
            borrowDetail.setBorrowId(vo.getBorrowId());
            borrowDetail.setIsbn(vo.getListDetail().get(i).getIsbn());
            borrowDetail.setReturnedDate(null);
            borrowDetail.setBorrowStatus(BorrowStatus.BORROWING);
            borrowDetail.setNote(vo.getListDetail().get(i).getNote());

            listBorrowDetail.add(borrowDetail);
        }

        borrowCard.setBorrowDetail(listBorrowDetail);
        borrowRepository.save(borrowCard);

        return borrowCard;
    }

    /**
     * 5. Lập phiếu trả sách
     * @param vo
     * @return
     */
    public BorrowCard setReturnedCard(BorrowVO vo){
        authorValidator.validate(PERMISSION_OF_FUNCTION);
        BorrowCard borrowCard = borrowRepository.findByBorrowId(vo.getBorrowId());
        List<BorrowDetail>  borrowDetail = borrowCard.getBorrowDetail();

        if(!borrowDetail.toString().contains(BorrowStatus.BORROWING.toString())) {
            System.out.println("Thẻ mượn sách này đã được trả hết.");
            return null;
        }
        // set all status
        for (int i = 0; i < borrowDetail.size(); i++){
            if(vo.getListDetail().get(i).getBorrowStatus() == null) {
                borrowDetail.get(i).setBorrowStatus(BorrowStatus.RETURNED);
            } else {
                borrowDetail.get(i).setBorrowStatus(vo.getListDetail().get(i).getBorrowStatus());
            }

            if (vo.getListDetail().get(i).getBorrowStatus() == BorrowStatus.LOST) {
                borrowDetail.get(i).setReturnedDate(null);
                try {
                    bookRepository.updateTotalByIsbn(vo.getListDetail().get(i).getIsbn(), -1);
                } catch (Exception e) {
                    System.out.println("Cập nhật số lượng sách thất bại do lỗi: " + e.getMessage());
                }
            } else {
                borrowDetail.get(i).setReturnedDate(vo.getListDetail().get(i).getReturnedDate());
            }

            borrowDetail.get(i).setNote(vo.getListDetail().get(i).getNote());

            try {
                borrowDetailRepository.update(borrowDetail.get(i));
            } catch (Exception e) {
                System.out.println("Cập nhật thẻ mượn sách thất bại do lỗi: " + e.getMessage());
            }
        }

        borrowCard.setBorrowDetail(borrowDetail);

        return borrowCard;
    }

    public BorrowCard findByBorrowId(String borrowId){
        return borrowRepository.findByBorrowId(borrowId);
    }
}
