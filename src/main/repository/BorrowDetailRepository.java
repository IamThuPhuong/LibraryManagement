package main.repository;

import main.entity.BorrowDetail;
import main.enums.BorrowStatus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static main.repository.ReaderRepository.removeEmptyLines;

public class BorrowDetailRepository {
    private final String DETAIL_FILE_PATH = "src/test/data/borrowDetail.csv";

    public Stream<BorrowDetail> stream(){
        Path path = Path.of(DETAIL_FILE_PATH);
        try {
            return Files.lines(path).map(line -> {
                String[] parts = line.split("\\|", -1);
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
    public List<BorrowDetail> list(String borrowId, Boolean isReturn, Boolean isLate){
        try {
            Stream<BorrowDetail> stream = this.stream();

            if (borrowId != null) {
                stream = stream
                        .filter(borrowDetail -> borrowDetail != null)
                        .filter(borrowDetail -> borrowDetail.getBorrowId().equals(borrowId));

            }
            if (isReturn != null) {
                if (isReturn) {
                    stream = stream
                            .filter(borrowDetail -> borrowDetail.getReturnedDate() != null);
                } else {
                    stream = stream
                            .filter(borrowDetail -> borrowDetail.getReturnedDate() == null);
                }

            }

            if (isLate != null) {
                if (isLate) {
                    stream = stream
                            .filter(borrowDetail -> borrowDetail.getReturnedDate().isAfter(LocalDate.now()));
                } else {
                    stream = stream
                            .filter(borrowDetail -> !borrowDetail.getReturnedDate().isAfter(LocalDate.now()));
                }
            }

            return stream.toList();

        } catch (NullPointerException e){
            System.out.println("Không có data thỏa điều kiện");
            return List.of();
        }

    }

    public void save(BorrowDetail borrowDetail) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/data/borrowDetail.csv", true))) {
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
        removeEmptyLines("src/test/data/borrowDetail.csv");
    }

    public int countByIsbn(String isbn) {
        return (int) this.stream()
                .filter(detail -> detail.getIsbn().equals(isbn))
                .count();
    }

    public BorrowDetail findByIsbn(String isbn){
        return this.stream()
                .filter(detail -> detail.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public void update(BorrowDetail updateBorrowDetail) throws IOException {
        try {
            if (updateBorrowDetail == null) {
                throw new IllegalArgumentException("Check lại file borrowDetail.csv!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        List<BorrowDetail> borrowDetails = new ArrayList<>(this.list(null, null, null)/*get all*/);
        for (int i = 0; i < borrowDetails.size(); i++) {
            if (borrowDetails.get(i).getBorrowId().equals(updateBorrowDetail.getBorrowId())
                    && borrowDetails.get(i).getIsbn().equals(updateBorrowDetail.getIsbn())) {
                borrowDetails.set(i, updateBorrowDetail);
                break;
            }
        }
        overwrite(borrowDetails);
    }

    private static void overwrite(List<BorrowDetail> borrowDetails) throws IOException {
        Path path = Path.of("src/test/data/borrowDetail.csv");
        StringBuilder sb = new StringBuilder();
        for (BorrowDetail borrowDetail : borrowDetails) {
            sb.append("\n").append(borrowDetail.getBorrowId()).append("|")
                    .append(borrowDetail.getIsbn()).append("|")
                    .append(borrowDetail.getReturnedDate()).append("|")
                    .append(borrowDetail.getBorrowStatus()).append("|")
                    .append(borrowDetail.getNote())
                    .append("\n");
        }

        try {
            Files.writeString(path, sb.toString(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e){
            throw new IOException("Lỗi khi ghi file");
        }
        removeEmptyLines("src/test/data/borrowDetail.csv");
    }

}
