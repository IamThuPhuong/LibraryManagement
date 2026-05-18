package main.validate;

import main.constants.ErrConstants;
import main.info.Book;
import main.repositories.BookRepository;
import main.vo.BorrowDetailVO;

import java.util.ArrayList;
import java.util.List;

public class BorrowValidator implements Validator<BorrowDetailVO>{
    private final BookRepository bookRepository = new BookRepository();

    @Override
    public List<String> validate(BorrowDetailVO target) {
        // TODO: Check lại phần check null
        List<String> errList = new ArrayList<>();

        if (target.getBorrowId() == null){
            errList.add(ErrConstants.BORROW_ID_CANNOT_NULL);
        }

        if(target.getReaderId() == null){
            errList.add(ErrConstants.BORROW_READER_CANNOT_NULL);
        }

        if(target.getBorrowDate() == null){
            errList.add(ErrConstants.BORROW_DATE_CANNOT_NULL);
        }

        if(target.getIsbn() == null){
            errList.add(ErrConstants.BORROW_BOOK_CANNOT_NULL);
        }

        if(target.getAmount() == 0){
            errList.add(ErrConstants.BORROW_AMOUNT_NOT_ZERO);
        }

        for (String eachBook : target.getIsbn()) {
            Book existedBook = bookRepository.findByISBN(eachBook);
            if (existedBook.getTotal() < 1) {
                errList.add(ErrConstants.NOT_ENOUGH_BOOK + " " + existedBook.getName());
            }
        }

        if (target.getAmount() > 10){
            errList.add(ErrConstants.BORROW_UNDER_10);
        }
        return errList;
    }
}
