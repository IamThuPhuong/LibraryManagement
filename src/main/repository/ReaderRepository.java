package main.repository;

import main.enums.Gender;
import main.entity.ReaderCard;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReaderRepository {

    public Stream<ReaderCard> stream() {
        Path path = Path.of("src/test/data/readerCard.csv");
        try {
            return Files.lines(path).map(line -> {
                String[] parts = line.split("\\|");
                try{
                    if (parts.length != 9) {
                        throw new RuntimeException("Invalid user data format:" + line);
                    }
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                    return null; // Trả về null nếu dữ liệu không hợp lệ
                }
                ReaderCard readerCard = new ReaderCard();
                readerCard.setReaderId(parts[0]);
                readerCard.setFullName(parts[1]);
                readerCard.setIdCard(parts[2]);
                readerCard.setBirthDate(LocalDate.parse(parts[3]));
                if (parts[4] != null && !parts[4].isEmpty() && !parts[4].equals("null")){
                    readerCard.setGender(Gender.valueOf(parts[4]));
                }  else {
                    readerCard.setGender(Gender.OTHER);
                }
                readerCard.setEmail(parts[5]);
                readerCard.setAddress(parts[6]);
                if (parts[7] != null && !parts[7].isEmpty() && !parts[7].equals("null")) {
                    readerCard.setStartDate(LocalDate.parse(parts[7]));
                }
                if (parts[8] != null && !parts[8].isEmpty() && !parts[8].equals("null")) {
                    readerCard.setEndDate(LocalDate.parse(parts[8]));
                }
                return readerCard;
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read reader card data: " + e.getMessage(), e);
        }

    }

    public List<ReaderCard> getAllReaders(){
        return this.list(null, null);
    }

    public ReaderCard findByIdCardNo(String idCardNo) {
        return this.stream()
                .filter(readerCard -> readerCard.getIdCard().equals(idCardNo))
                .findFirst()
                .orElse(null);
    }

    public List<ReaderCard> findByName(String name){
        return this.list(name, null);
    }

    public int countAllReader(){
        return Math.toIntExact(this.stream().count());
    }

    public void saveReaderCartToFile(ReaderCard readerCard)  {
        // Thay thế bằng SQL khi chuyển sang Spring
        // Ghi đè file readerCard.csv với nội dung mới từ readerList
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/data/readerCard.csv", true))){
            writer.newLine();
            String line = readerCard.getReaderId() + "|"
                    + readerCard.getFullName() + "|"
                    + readerCard.getIdCard() + "|"
                    + readerCard.getBirthDate() + "|"
                    + readerCard.getGender() + "|"
                    + readerCard.getEmail() + "|"
                    + readerCard.getAddress() + "|"
                    +  readerCard.getStartDate()  + "|"
                    +  readerCard.getEndDate() ;
            writer.newLine();
            writer.write(line);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
        removeEmptyLines("src/test/data/readerCard.csv");
    }

    public void updateReaderCard(ReaderCard readerCard) throws IOException {
        try {
            if (readerCard == null) {
                throw new IllegalArgumentException("Check lại file readerCard.csv!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        List<ReaderCard> readerCards = new ArrayList<>(this.getAllReaders());
        for (int i = 0; i < readerCards.size(); i++) {
            if (readerCards.get(i).getReaderId().equals(readerCard.getReaderId())) {
                readerCards.set(i, readerCard);
                break;
            }
        }
        overwriteReaderCards(readerCards);
    }

    public void delete(String deleteReaderId){
        try{
            if (deleteReaderId == null){
                throw new IllegalArgumentException("Reader Id không tồn tại!");
            }
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return;
        }
        List<ReaderCard> readerCards = new ArrayList<>(this.getAllReaders());
        for (int i = 0; i < readerCards.size(); i++){
            if(readerCards.get(i).getReaderId().equals(deleteReaderId)){
                readerCards.remove(readerCards.get(i));
                break;
            }
        }
        try {
            overwriteReaderCards(readerCards);
        } catch (IOException e){
            System.out.println("Ghi file thất bại! Lỗi: " + e);
        }
    }

    private static void overwriteReaderCards(List<ReaderCard> readerCards) throws IOException {
        // Ghi đè file readerCard.csv với nội dung mới từ readerList sau khi đã cập nhật readerCard
        Path path = Path.of("src/test/data/readerCard.csv");
        StringBuilder sb = new StringBuilder();
        for (ReaderCard readerCard1 : readerCards) {
            sb.append("\n").append(readerCard1.getReaderId()).append("|")
                    .append(readerCard1.getFullName()).append("|")
                    .append(readerCard1.getIdCard()).append("|")
                    .append(readerCard1.getBirthDate()).append("|")
                    .append(readerCard1.getGender() != null ? readerCard1.getGender().name() : "null").append("|")
                    .append(readerCard1.getEmail()).append("|")
                    .append(readerCard1.getAddress()).append("|")
                    .append(readerCard1.getStartDate()).append("|")
                    .append(readerCard1.getEndDate()).append("|")
                    .append("\n");
        }

        try {
            Files.writeString(path, sb.toString(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e){
            throw new IOException("Lỗi khi ghi file");
        }
        removeEmptyLines("src/test/data/readerCard.csv");
    }

    public static void removeEmptyLines(String filePath) {
        try {
            Path path = Paths.get(filePath);

            // 1. Đọc toàn bộ các dòng trong file vào bộ nhớ
            List<String> lines = Files.readAllLines(path);

            // 2. Lọc bỏ các dòng trống hoặc chỉ chứa khoảng trắng (trim)
            List<String> filteredLines = lines.stream()
                    .filter(line -> line != null && !line.trim().isEmpty())
                    .collect(Collectors.toList());

            // 3. Ghi đè danh sách dòng đã lọc ngược lại vào file
            Files.write(path, filteredLines);

            System.out.println("Đã xóa các dòng trống thành công!");

        } catch (IOException e) {
            System.err.println("Lỗi khi xử lý file: " + e.getMessage());
        }
    }

    public List<ReaderCard> list(String name, Gender gender){
        Stream<ReaderCard> stream = this.stream();
        try {
            // TODO: làm phần tìm kiếm có dấu và lowercase
            if (name != null) {
                stream = stream.filter(readerCard -> readerCard.getFullName().contains(name));
            }

            if (gender != null) {
                stream = stream.filter(readerCard -> readerCard.getGender() == gender);
            }

            return stream.toList();
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm reader: " + e.getMessage());
            return List.of();
        }
    }
}
