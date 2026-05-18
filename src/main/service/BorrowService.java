package main.service;

import main.enums.BorrowStatus;
import main.info.BorrowCard;
import main.info.BorrowDetail;
import main.validate.BorrowValidator;
import main.validate.Validator;
import main.vo.BorrowDetailVO;

import java.util.ArrayList;
import java.util.List;

public class BorrowService {
    private Validator<BorrowDetailVO> borrowValidator = new BorrowValidator();

    public BorrowCard setBorrowCard(BorrowDetailVO vo){
        BorrowCard borrowCard = new BorrowCard();
        BorrowDetail borrowDetail = new BorrowDetail();
        List<BorrowDetail> listBorrowDetail = new ArrayList<>();
        // TODO: Check lại phần lập phiếu mượn sách
        List<String> errList = borrowValidator.validate(vo);
        if (errList != null){
            return null;
        }
        borrowCard.setBorrowId(vo.getBorrowId());
        borrowCard.setReaderId(vo.getReaderId());
        borrowCard.setBorrowDate(vo.getBorrowDate());
        borrowCard.setDueDate(vo.getBorrowDate().plusMonths(1));

        for (int i = 0; i < vo.getAmount(); i++) {
            borrowDetail.setBorrowId(vo.getBorrowId());
            borrowDetail.setIsbn(vo.getIsbn().get(i));
            borrowDetail.setReturnedDate(null);
            borrowDetail.setBorrowStatus(BorrowStatus.BORROWING);

            listBorrowDetail.add(borrowDetail);
        }

        borrowCard.setBorrowDetail(listBorrowDetail);

        return borrowCard;
    }
}
