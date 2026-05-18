package main.repositories;

import main.enums.BorrowStatus;
import main.info.BorrowCard;
import main.info.BorrowDetail;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static main.repositories.ReaderRepository.removeEmptyLines;

public class BorrowDetailRepository {
    private final String DETAIL_FILE_PATH = "src/test/data/borrowDetail.csv";

    public Stream<BorrowDetail> stream(){
        Path path = Path.of(DETAIL_FILE_PATH);
        try {
            return Files.lines(path).map(line -> {
                String[] parts = line.split("\\|");
                try {
                    if (parts.length != 5) {
                        throw new RuntimeException("Định dạng không hợp lệ: " + line);
                    }
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                    return null;
                }
                BorrowDetail detail = new BorrowDetail();
                detail.setBorrowId(parts[0]);
                detail.setIsbn(parts[1]);

                // Kiểm tra ngày trả thực tế (có thể null nếu chưa trả)
                if (parts[2] != null && !parts[2].isEmpty() && !parts[2].equals("null")) {
                    detail.setReturnedDate(LocalDate.parse(parts[2]));
                }

                detail.setBorrowStatus(BorrowStatus.valueOf(parts[3]));
                detail.setNote(parts[4].equals("null") ? "" : parts[4]);
                return detail;
            });
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc phiếu mượn chi tiết: " + e.getMessage(), e);
        }
    }

    /**
     * Lọc theo param
     * @param borrowId
     * @param isReturn
     * @return
     */
    public List<BorrowDetail> list(String borrowId, boolean isReturn){
        // TODO: Dùng cho thống kê
        List<BorrowDetail> detail = this.stream().toList();
        if (borrowId != null){
            detail = this.stream()
                    .filter(borrowDetail -> borrowDetail.getBorrowId().equals(borrowId))
                    .toList();
        }
        if (!isReturn){
            detail = this.stream()
                    .filter(borrowDetail -> borrowDetail.getBorrowStatus().equals(BorrowStatus.RETURNED))
                    .toList();
        }
        return detail;
    }

    public void save(BorrowDetail borrowDetail) {
        // Thay thế bằng SQL khi chuyển sang Spring
        // Ghi đè file book.csv với nội dung mới từ danh sách book
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/data/borrowCard.csv", true))) {
            writer.newLine();
            // Nối các thuộc tính của Book cách nhau bằng dấu "|"
            String line = borrowDetail.getBorrowId() + "|"
                    + borrowDetail.getIsbn() + "|"
                    + borrowDetail.getReturnedDate() + "|"
                    + borrowDetail.getBorrowStatus() + "|"
                    + borrowDetail.getNote();
            writer.newLine();
            writer.write(line);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi phiếu mượn chi tiết: " + e.getMessage());
        }
        removeEmptyLines("src/test/data/borrowCard.csv");
    }



}
