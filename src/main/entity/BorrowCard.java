package main.entity;

import main.repository.BorrowDetailRepository;
import main.repository.BorrowRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowCard {
    private String borrowId;
    private String readerId;
    private LocalDate borrowDate;
    /** Sau ngày mượn 1 tháng */
    private LocalDate dueDate;
    private List<BorrowDetail> borrowDetail = new ArrayList<>();

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

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public List<BorrowDetail> getBorrowDetail() {
        return borrowDetail;
    }

    public void setBorrowDetail(List<BorrowDetail> borrowDetail) {
        this.borrowDetail = borrowDetail;
    }

    @Override
    public String toString() {
        return "BorrowCard{" +
                "borrowId='" + borrowId + '\'' +
                ", readerId='" + readerId + '\'' +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", borrowDetail=" + borrowDetail.toString() +
                '}';
    }
}
