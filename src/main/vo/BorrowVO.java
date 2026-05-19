package main.vo;

import main.constants.Constants;

import java.time.LocalDate;
import java.util.List;

public class BorrowVO {
    private String borrowId = Constants.INIT_STRING;
    private String readerId = Constants.INIT_STRING;
    private int amount = Constants.INT_0;
    private LocalDate borrowDate = Constants.TODAY;
    private List<BorrowDetailVO> listDetail;

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setBorrowDate(String birthdaystr) {
        // Kiểm tra null hoặc rỗng
        if (birthdaystr == null || birthdaystr.trim().isEmpty()) {
            this.borrowDate = LocalDate.parse(
                    Constants.STRING_INIT_DATE,
                    Constants.DATE_FORMATTER
            );
            return;
        }

        try {
            // Parse String thành LocalDate theo format
            this.borrowDate = LocalDate.parse(birthdaystr, Constants.DATE_FORMATTER);
        } catch (Exception e) {
            System.out.println("Định dạng ngày sinh không hợp lệ: " + birthdaystr);
            System.out.println("Vui lòng sử dụng định dạng: dd/MM/yyyy");
            // Sử dụng giá trị mặc định nếu parse lỗi
            this.borrowDate = LocalDate.parse(
                    Constants.STRING_INIT_DATE,
                    Constants.DATE_FORMATTER
            );
        }
    }
    public List<BorrowDetailVO> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<BorrowDetailVO> listDetail) {
        this.listDetail = listDetail;
    }
}
