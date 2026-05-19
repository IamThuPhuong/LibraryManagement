package main.validate;

import main.constants.ErrConstants;
import main.info.Book;
import main.repositories.BookRepository;
import main.vo.BorrowVO;

import java.util.ArrayList;
import java.util.List;

public class BorrowValidator implements Validator<BorrowVO>{
    private final BookRepository bookRepository = new BookRepository();

    @Override
    public List<String> validate(BorrowVO target) {
        List<String> errList = new ArrayList<>();

        if (target.getBorrowId() == null || target.getBorrowId().trim().isEmpty()){
            errList.add(ErrConstants.BORROW_ID_CANNOT_NULL);
        }

        if(target.getReaderId() == null || target.getReaderId().trim().isEmpty()){
            errList.add(ErrConstants.BORROW_READER_CANNOT_NULL);
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
                if (existedBook == null) {
                    errList.add(ErrConstants.ISBN_CAN_NOT_NULL + " " + eachBook);
                    continue;
                }
                if (existedBook.getTotal() < 1) {
                    errList.add(ErrConstants.NOT_ENOUGH_BOOK + " " + existedBook.getName());
                }
            }
        }



        return errList;
    }
}
