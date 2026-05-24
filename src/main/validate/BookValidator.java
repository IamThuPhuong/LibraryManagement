package main.validate;

import main.constants.Constants;
import main.constants.ErrConstants;
import main.vo.BookVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookValidator implements Validator<BookVO>{

    @Override
    public List<String> validate(BookVO target) {
        List<String> errList = new ArrayList<>();
        if (target.getIsbn() == null || target.getIsbn().isEmpty()) {
            errList.add(ErrConstants.ISBN_CAN_NOT_NULL);
        }

        if(target.getName() == null || target.getName().isEmpty()){
            errList.add(ErrConstants.NAME_CAN_NOT_NULL);
        }

        if(target.getAuthor() == null || target.getAuthor().isEmpty()){
            errList.add(ErrConstants.AUTHOR_CAN_NOT_NULL);
        }

        if(target.getPublisher() == null || target.getPublisher().isEmpty()){
            errList.add(ErrConstants.PUBLISHER_CAN_NOT_NULL);
        }

        if(target.getPublishYear() == null || target.getPublishYear().isEmpty()){
            errList.add(ErrConstants.PUBLISHYEAR_CAN_NOT_NULL);
        }

        // TODO: Check existed cho isbn

        if(target.getPrice() == null || target.getPrice() < Constants.INT_0){
            errList.add(ErrConstants.PRICE_INVALID);
        }

        if(target.getTotal() == null || target.getTotal() < Constants.INT_0){
            errList.add(ErrConstants.TOTAL_INVALID);
        }

        try {
            if (isPublishYearInFuture(target.getPublishYear())) {
                errList.add(ErrConstants.PUBLISHYEAR_IN_FUTURE);
            }
        } catch (NullPointerException e){
            System.out.println("Năm xuất bản không được để trống!");
            return errList;
        }

        return errList;
    }

    private static boolean isPublishYearInFuture (String year){
        if (year == null){
            throw new NullPointerException("Năm xuất bản không được để trống");
        }
        return Integer.parseInt(year) > LocalDate.now().getYear();
    }
}
