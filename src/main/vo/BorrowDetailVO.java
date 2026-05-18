package main.vo;

import main.constants.Constants;

import java.time.LocalDate;
import java.util.List;

public class BorrowDetailVO {
    private String borrowId = Constants.INIT_STRING;
    private String readerId = Constants.INIT_STRING;
    private int amount = Constants.INT_0;
    private List<String> isbn = List.of();
    private LocalDate borrowDate = Constants.INIT_DATE;
    private String note = Constants.INIT_STRING;

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

    public List<String> getIsbn() {
        return isbn;
    }

    public void setIsbn(List<String> isbn) {
        this.isbn = isbn;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
