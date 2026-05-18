package main.repositories;

import main.info.BorrowCard;
import main.info.BorrowDetail;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Stream;

import static main.repositories.ReaderRepository.removeEmptyLines;

public class BorrowRepository {
    private final String CARD_FILE_PATH = "src/test/data/borrowCard.csv";

    public final BorrowDetailRepository borrowDetailRepository = new BorrowDetailRepository();

    public Stream<BorrowCard> stream() {
        Path path = Path.of(CARD_FILE_PATH);
        try {
            return Files.lines(path).map(line -> {
                String[] parts = line.split("\\|");
                try {
                    if (parts.length != 4) {
                        throw new RuntimeException("Invalid borrow card format: " + line);
                    }
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                    return null;
                }
                BorrowCard card = new BorrowCard();
                card.setBorrowId(parts[0]);
                card.setReaderId(parts[1]);
                card.setBorrowDate(LocalDate.parse(parts[2]));
                card.setDueDate(LocalDate.parse(parts[3]));
                card.setBorrowDetail(borrowDetailRepository.list(parts[0], false));
                return card;
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read borrow card data: " + e.getMessage(), e);
        }
    }

    public void save(BorrowCard borrowCard) {
        // Thay thế bằng SQL khi chuyển sang Spring
        // Ghi đè file book.csv với nội dung mới từ danh sách book
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/data/borrowCard.csv", true))) {
            writer.newLine();
            // Nối các thuộc tính của Book cách nhau bằng dấu "|"
            String line = borrowCard.getBorrowId() + "|"
                    + borrowCard.getReaderId() + "|"
                    + borrowCard.getBorrowDate() + "|"
                    + borrowCard.getDueDate();
            writer.newLine();
            writer.write(line);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi phiếu mượn: " + e.getMessage());
        }
        removeEmptyLines("src/test/data/borrowCard.csv");

        // Lưu thông tin chi tiết
        for (BorrowDetail borrowDetail : borrowCard.getBorrowDetail()){
            borrowDetailRepository.save(borrowDetail);
        }
    }
}
