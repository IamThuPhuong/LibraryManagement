package main.service;

import main.constants.Constants;
import main.enums.BorrowStatus;
import main.enums.Permission;
import main.info.BorrowCard;
import main.info.BorrowDetail;
import main.info.User;
import main.repositories.BorrowRepository;
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
}
