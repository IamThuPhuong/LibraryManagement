package main.validate;

import main.constants.ErrConstants;
import main.entity.Book;
import main.repository.BookRepository;
import main.repository.BorrowDetailRepository;
import main.repository.BorrowRepository;
import main.vo.BorrowVO;

import java.util.ArrayList;
import java.util.List;

public class BorrowValidator implements Validator<BorrowVO>{
    private final BookRepository bookRepository = new BookRepository();
    private final BorrowRepository borrowRepository = new BorrowRepository();
    private final BorrowDetailRepository borrowDetailRepository = new BorrowDetailRepository();

    @Override
    public List<String> validate(BorrowVO target) {
        List<String> errList = new ArrayList<>();

        if (target.getBorrowId() == null || target.getBorrowId().trim().isEmpty()){
            errList.add(ErrConstants.BORROW_ID_CANNOT_NULL);
        }

        if(target.getReaderId() == null || target.getReaderId().trim().isEmpty()){
            errList.add(ErrConstants.BORROW_READER_CANNOT_NULL);
        }

        if(checkBorrowIDExist(target.getBorrowId())){
            errList.add(ErrConstants.BORROW_ID_EXIST);
        }

        if(target.getAmount() == 0) {
            errList.add(ErrConstants.BORROW_AMOUNT_NOT_ZERO);
        } else if(target.getAmount() > 10){
            errList.add(ErrConstants.BORROW_UNDER_10);
        } else {
            for (int i = 0; i < target.getAmount(); i++) {
                String eachBook = target.getListDetail().get(i).getIsbn();
                if (eachBook == null || eachBook.trim().isEmpty()){
                    errList.add(ErrConstants.ISBN_CAN_NOT_NULL);
                }

                Book existedBook = bookRepository.findByISBN(eachBook);
                int countBorrowBook = borrowDetailRepository.countByIsbn(eachBook);
                if (existedBook == null) {
                    errList.add(ErrConstants.ISBN_CAN_NOT_NULL + " " + eachBook);
                    continue;
                }
                // Sách còn lại = tổng sách thư viện sở hữu - sách đã mượn
                if (existedBook.getTotal() - countBorrowBook < 1) {
                    errList.add(ErrConstants.NOT_ENOUGH_BOOK + " " + existedBook.getName());
                }
            }
        }



        return errList;
    }

    private boolean checkBorrowIDExist(String borrowId) {
        return borrowRepository.findByBorrowId(borrowId) != null;
    }
}
