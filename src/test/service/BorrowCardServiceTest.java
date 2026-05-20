package test.service;

import main.info.BorrowCard;
import main.info.BorrowDetail;
import main.service.BorrowService;
import main.vo.BorrowDetailVO;
import main.vo.BorrowVO;

import java.util.List;
import java.util.Scanner;

public class BorrowCardServiceTest {
    private static final Scanner input = new Scanner(System.in);
    private static final BorrowService borrowService = new BorrowService();

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
}
