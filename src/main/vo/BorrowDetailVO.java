package main.vo;

import main.constants.Constants;
import main.enums.BorrowStatus;

import java.time.LocalDate;

public class BorrowDetailVO {
    private String isbn;
    private String note;
    private LocalDate returnedDate;
    private BorrowStatus borrowStatus;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public void setReturnedDate(String returnedDate){
        // Kiểm tra null hoặc rỗng
        if (returnedDate == null || returnedDate.trim().isEmpty()) {
            this.returnedDate = LocalDate.parse(
                    Constants.STR_TODAY,
                    Constants.DATE_FORMATTER
            );
            return;
        }

        try {
            // Parse String thành LocalDate theo format
            this.returnedDate = LocalDate.parse(returnedDate, Constants.DATE_FORMATTER);
        } catch (Exception e) {
            System.out.println("Định dạng ngày trả không hợp lệ: " + returnedDate);
            System.out.println("Vui lòng sử dụng định dạng: dd/MM/yyyy");
            // Sử dụng giá trị hôm nay nếu parse lỗi
            this.returnedDate = LocalDate.parse(
                    Constants.STR_TODAY,
                    Constants.DATE_FORMATTER
            );
        }
    }

    public BorrowStatus getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(BorrowStatus borrowStatus) {
        this.borrowStatus = borrowStatus;
    }
}
