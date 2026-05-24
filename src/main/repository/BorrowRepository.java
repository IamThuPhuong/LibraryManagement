package main.repository;

import main.entity.BorrowCard;
import main.entity.BorrowDetail;

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
                card.setBorrowDetail(borrowDetailRepository.list(parts[0], null, null));
                return card;
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read borrow card data: " + e.getMessage(), e);
        }
    }

    public List<BorrowCard> getAll() {
        return this.stream().toList();
    }

    public void save(BorrowCard borrowCard) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/data/borrowCard.csv", true))) {
            writer.newLine();
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

    public void update(BorrowCard updateBorrowCard) throws IOException {
        try {
            if (updateBorrowCard == null) {
                throw new IllegalArgumentException("Check lại file borrowCard.csv!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        List<BorrowCard> borrowCards = new ArrayList<>(this.getAll());
        for (int i = 0; i < borrowCards.size(); i++) {
            if (borrowCards.get(i).getBorrowId().equals(updateBorrowCard.getBorrowId())) {
                borrowCards.set(i, updateBorrowCard);
                break;
            }
        }
        // update detail
        for (BorrowDetail borrowDetail : updateBorrowCard.getBorrowDetail()){
            borrowDetailRepository.update(borrowDetail);
        }
        overwrite(borrowCards);
    }

    public BorrowCard findByBorrowId(String borrowId) {
        return this.stream()
                .filter(borrowCard -> borrowCard.getBorrowId().equals(borrowId))
                .findFirst()
                .orElse(null);
    }

    private static void overwrite(List<BorrowCard> borrowCards) throws IOException {
        Path path = Path.of("src/test/data/borrowCard.csv");
        StringBuilder sb = new StringBuilder();
        for (BorrowCard borrowCard : borrowCards) {
            sb.append("\n").append(borrowCard.getBorrowId()).append("|")
                    .append(borrowCard.getReaderId()).append("|")
                    .append(borrowCard.getBorrowDate()).append("|")
                    .append(borrowCard.getDueDate()).append("|")
                    .append("\n");
        }

        try {
            Files.writeString(path, sb.toString(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e){
            throw new IOException("Lỗi khi ghi file");
        }
        removeEmptyLines("src/test/data/borrowCard.csv");
    }

}
