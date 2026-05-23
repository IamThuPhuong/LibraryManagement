package test.service;

import main.entity.BorrowCard;
import main.entity.BorrowDetail;
import main.enums.BorrowStatus;
import main.service.BookService;
import main.service.BorrowService;
import main.vo.BorrowDetailVO;
import main.vo.BorrowVO;

import java.util.List;
import java.util.Scanner;

public class BorrowCardServiceTest {
    private static final Scanner input = new Scanner(System.in);
    private static final BorrowService borrowService = new BorrowService();
    private static final BookService bookService = new BookService();

    public void testCreateBorrowCard() {
        System.out.println("Nhập thông tin thẻ mượn sách:");
        System.out.print("1. Mã thẻ mượn sách (*):");
        String borrowId = input.nextLine();
        while (borrowId.isEmpty()) {
            System.out.println("Mã thẻ mượn sách không được để trống! Vui lòng nhập lại:");
            borrowId = input.nextLine();
        }
        System.out.print("\n2. Mã độc giả (*):");
        String readerId = input.nextLine();
        while (readerId.isEmpty()) {
            System.out.println("Mã độc giả không được để trống! Vui lòng nhập lại:");
            readerId = input.nextLine();
        }
        System.out.print("\n3. Ngày mượn (dd/MM/yyyy):");
        System.out.print("\n(Ngày mượn sẽ được mặc định là ngày hiện tại nếu để trống.)");
        String borrowDate = input.nextLine();
        System.out.println("\n4. Số lượng sách muốn mượn:");
        int totalBooks = input.nextInt();
        input.nextLine();
        System.out.println("Nhập thông tin chi tiết sách muốn mượn:");
        List<String> isbnList = new java.util.ArrayList<>();
        List<String> noteList = new java.util.ArrayList<>();
        for (int i = 0; i < totalBooks; i++) {
            System.out.println("Sách thứ " + (i + 1) + ":");
            System.out.print("Mã ISBN (*):");
            String isbn = input.nextLine();
            while (isbn.isEmpty()) {
                System.out.println("Mã ISBN không được để trống! Vui lòng nhập lại:");
                isbn = input.nextLine();
            }
            isbnList.add(isbn);
            System.out.print("Ghi chú (nếu có):");
            String note = input.nextLine();
            noteList.add(note);
        }

        BorrowVO vo = new BorrowVO();
        vo.setBorrowId(borrowId);
        vo.setReaderId(readerId);
        vo.setBorrowDate(borrowDate);
        vo.setAmount(totalBooks);
        List<BorrowDetailVO> detailList = new java.util.ArrayList<>();
        for(int i = 0; i < totalBooks; i++) {
            BorrowDetailVO detailVO = new BorrowDetailVO();
            detailVO.setIsbn(isbnList.get(i));
            detailVO.setNote(noteList.get(i));
            detailList.add(detailVO);
        }
        vo.setListDetail(detailList);

        BorrowCard borrowCard = borrowService.setBorrowCard(vo);
        if(borrowCard == null) {
            System.out.println("Tạo thẻ mượn sách thất bại do lỗi dữ liệu. Vui lòng kiểm tra lại thông tin đã nhập.");
            return;
        }
        System.out.println("==Kết thúc tiến trình tạo thẻ mượn sách==!");
        System.out.println("____Thông tin thẻ mượn sách vừa tạo:_________");
        System.out.println("| Mã thẻ mượn sách: " + borrowCard.getBorrowId());
        System.out.println("| Mã độc giả: " + borrowCard.getReaderId());
        System.out.println("| Ngày mượn: " + borrowCard.getBorrowDate());
        System.out.println("| Số lượng sách mượn: " + borrowCard.getBorrowDetail().size());
        System.out.println("| Chi tiết sách mượn:");
        for (BorrowDetail detail : borrowCard.getBorrowDetail()) {
            System.out.println("- Mã ISBN: " + detail.getIsbn() + ", Ghi chú: " + detail.getNote());
        }
        System.out.println("______________________________________________");
    }

    public void testCreateReturnedCard() {
        System.out.println("Nhập thông tin thẻ trả sách:");
        System.out.print("1. Mã thẻ mượn sách (*):");
        String borrowId = input.nextLine();
        while (borrowId.isEmpty()) {
            System.out.println("Mã thẻ mượn sách không được để trống! Vui lòng nhập lại:");
            borrowId = input.nextLine();
        }

        System.out.println("Check lại thông tin thẻ mượn sách...");
        BorrowCard borrowCard = borrowService.findByBorrowId(borrowId);
        if (borrowCard == null) {
            System.out.println("Không tìm thấy thẻ mượn sách với mã: " + borrowId);
            return;
        }
        System.out.println("Thông tin thẻ mượn sách:");
        System.out.println("| Mã thẻ mượn sách: " + borrowCard.getBorrowId());
        System.out.println("| Mã độc giả: " + borrowCard.getReaderId());
        System.out.println("| Ngày mượn: " + borrowCard.getBorrowDate());
        if(borrowCard.getBorrowDetail() == null || borrowCard.getBorrowDetail().isEmpty()) {
            System.out.println("| Số lượng sách mượn: 0");
            System.out.println("| Chi tiết sách mượn: Không có sách nào được mượn.");
            return;
        }
        System.out.println("| Số lượng sách mượn: " + borrowCard.getBorrowDetail().size());
        System.out.println("| Chi tiết sách mượn:");
        for (BorrowDetail detail : borrowCard.getBorrowDetail()) {
            System.out.println("\tMã ISBN: " + detail.getIsbn() + ", \n\tGhi chú: " + detail.getNote() + ", \n\tTrạng thái: " + detail.getBorrowStatus());
        }

        System.out.println("Sách trả thuộc diện nào?: \n1. Trả đủ sách \n2. Trả thiếu sách");
        int returnType = input.nextInt();
        input.nextLine();
        List<BorrowDetailVO> detailVOList = new java.util.ArrayList<>();
        if (returnType == 1) {
            for (BorrowDetail detail : borrowCard.getBorrowDetail()) {
                BorrowDetailVO detailVO = new BorrowDetailVO();
                detailVO.setBorrowStatus(BorrowStatus.RETURNED);
                System.out.println("Nhập ngày trả sách (dd/MM/yyyy) cho sách có mã ISBN: " + detail.getIsbn());
                System.out.println("(Ngày trả sẽ được mặc định là ngày hiện tại nếu để trống.)");
                String returnedDate = input.nextLine();
                detailVO.setReturnedDate(returnedDate);
                System.out.println("Nhập ghi chú (nếu có) cho sách có mã ISBN: " + detail.getIsbn());
                System.out.println("(Ghi chú sẽ giữ nguyên nếu để trống.)");
                String note = input.nextLine();
                detailVO.setNote(note.isEmpty() ? detail.getNote() : note);
                detailVOList.add(detailVO);
            }
        } else {
            for (BorrowDetail detail : borrowCard.getBorrowDetail()) {
                BorrowDetailVO detailVO = new BorrowDetailVO();
                System.out.println("Chọn sách cần cập nhật:");
                for (int i = 0; i < borrowCard.getBorrowDetail().size(); i++) {
                    System.out.println((i + 1) + ". Mã ISBN: " + borrowCard.getBorrowDetail().get(i).getIsbn()
                            + ", Tên sách: " + bookService.findByISBN(borrowCard.getBorrowDetail().get(i).getIsbn()).getName()
                            + ", Trạng thái: " + borrowCard.getBorrowDetail().get(i).getBorrowStatus());
                }
                int bookNo = input.nextInt();
                input.nextLine();
                if (bookNo < 1 || bookNo > borrowCard.getBorrowDetail().size()) {
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                    return;
                }
                BorrowDetail selectedDetail = borrowCard.getBorrowDetail().get(bookNo - 1);
                System.out.println("Bạn đã chọn sách có mã ISBN: " + selectedDetail.getIsbn());
                System.out.println("Cập nhật trạng thái cho sách này:");
                System.out.println("1. Trả muộn");
                System.out.println("2. Mất sách");
                System.out.println("3. Đã trả");
                int statusChoice = input.nextInt();
                input.nextLine();
                if (statusChoice == 1) {
                    detailVO.setBorrowStatus(BorrowStatus.OVERDUE);
                    System.out.println("Nhập ghi chú (nếu có) cho sách có mã ISBN: " + selectedDetail.getIsbn());
                    System.out.println("(Ghi chú sẽ giữ nguyên nếu để trống.)");
                    String note = input.nextLine();
                    detailVO.setNote(note.isEmpty() ? selectedDetail.getNote() : note);
                } else if (statusChoice == 2) {
                    detailVO.setBorrowStatus(BorrowStatus.LOST);
                } else if (statusChoice == 3) {
                    // TODO: clean lại chỗ này
                    detailVO.setBorrowStatus(BorrowStatus.RETURNED);
                    System.out.println("Nhập ngày trả sách (dd/MM/yyyy) cho sách có mã ISBN: " + selectedDetail.getIsbn());
                    System.out.println("(Ngày trả sẽ được mặc định là ngày hiện tại nếu để trống.)");
                    String returnedDate = input.nextLine();
                    detailVO.setReturnedDate(returnedDate);
                    System.out.println("Nhập ghi chú (nếu có) cho sách có mã ISBN: " + selectedDetail.getIsbn());
                    System.out.println("(Ghi chú sẽ giữ nguyên nếu để trống.)");
                    String note = input.nextLine();
                    detailVO.setNote(note.isEmpty() ? selectedDetail.getNote() : note);
                } else {
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                    return;
                }
                detailVOList.add(detailVO);
            }

        }
        BorrowVO vo = new BorrowVO();
        vo.setBorrowId(borrowId);
        vo.setListDetail(detailVOList);
        BorrowCard updatedCard = borrowService.setReturnedCard(vo);

        System.out.println("==Kết thúc tiến trình lập thẻ trả sách==!");
        System.out.println("____Thông tin thẻ trả sách vừa cập nhật:_________");
        System.out.println("| Mã thẻ mượn sách: " + updatedCard.getBorrowId());
        System.out.println("| Mã độc giả: " + updatedCard.getReaderId());
        System.out.println("| Ngày mượn: " + updatedCard.getBorrowDate());
        System.out.println("| Số lượng sách mượn: " + updatedCard.getBorrowDetail().size());
        System.out.println("| Chi tiết sách mượn:");
        for (BorrowDetail detail : updatedCard.getBorrowDetail()) {
            System.out.println("- Mã ISBN: " + detail.getIsbn() + "\n- Ghi chú: " + detail.getNote() + "\n- Trạng thái: " + detail.getBorrowStatus() + "\n- Ngày trả: " + detail.getReturnedDate());
        }
        System.out.println("______________________________________________");
    }

}
