package main.entity;

import main.enums.BorrowStatus;

import java.time.LocalDate;

public class BorrowDetail {
    private String borrowId;
    private String isbn;
    /** Ngày trả thực tế */
    private LocalDate returnedDate;
    private BorrowStatus borrowStatus;
    private String note;

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public BorrowStatus getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(BorrowStatus borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "BorrowDetail{" +
                "borrowId='" + borrowId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", returnedDate=" + returnedDate +
                ", borrowStatus=" + borrowStatus +
                ", note='" + note + '\'' +
                '}';
    }
}
