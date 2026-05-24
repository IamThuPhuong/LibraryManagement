package main.repository;

import main.enums.Genre;
import main.entity.Book;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static main.repository.ReaderRepository.removeEmptyLines;

public class BookRepository {
    public Stream<Book> stream() {
        Path path = Path.of("src/test/data/book.csv");
        try {
            return Files.lines(path).map(line -> {
                String[] parts = line.split("\\|");
                try {
                    if (parts.length != 8) {
                        throw new RuntimeException("Invalid book data format: " + line);
                    }
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                    return null; // Trả về null nếu dòng dữ liệu không hợp lệ
                }

                Book book = new Book();
                book.setIsbn(parts[0]);
                book.setName(parts[1]);
                book.setAuthor(parts[2]);
                book.setPublisher(parts[3]);
                book.setPublishYear(parts[4]);
                if (parts[5] != null && !parts[5].isEmpty() && !parts[5].equals("null")) {
                    book.setGenre(Genre.fromString(parts[5]));
                } else {
                    book.setGenre(Genre.OTHER);
                }
                if (parts[6] != null && !parts[6].isEmpty() && !parts[6].equals("null")) {
                    book.setPrice(Long.parseLong(parts[6].trim()));
                }

                if (parts[7] != null && !parts[7].isEmpty() && !parts[7].equals("null")) {
                    book.setTotal(Integer.parseInt(parts[7].trim()));
                }

                return book;
            }).filter(Objects::nonNull);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read book data: " + e.getMessage(), e);
        }
    }

    public List<Book> getAllBooks() {
        return this.list(null, null);
    }

    public void saveBookToFile(Book book) {
        // Thay thế bằng SQL khi chuyển sang Spring
        // Ghi đè file book.csv với nội dung mới từ danh sách book
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/data/book.csv", true))) {
            writer.newLine();
            // Nối các thuộc tính của Book cách nhau bằng dấu "|"
            String line = book.getIsbn() + "|"
                    + book.getName() + "|"
                    + book.getAuthor() + "|"
                    + book.getPublisher() + "|"
                    + book.getPublishYear() + "|"
                    + book.getGenre() + "|"
                    + book.getPrice() + "|"
                    + book.getTotal();
            writer.newLine();
            writer.write(line);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file sách: " + e.getMessage());
        }
        removeEmptyLines("src/test/data/book.csv");
    }

    public void update(Book book) throws IOException {
        try {
            if (book == null) {
                throw new IllegalArgumentException("Check lại file book.csv!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        List<Book> books = new ArrayList<>(this.getAllBooks());

        for (int i = 0; i < books.size(); i++) {
            // So sánh mã ISBN để tìm sách cần cập nhật
            if (books.get(i).getIsbn().equals(book.getIsbn())) {
                books.set(i, book);
                break;
            }
        }

        overwriteBooks(books);
    }

    public void delete(String deleteIsbn) {
        try {
            if (deleteIsbn == null) {
                throw new IllegalArgumentException("Book Id không tồn tại!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        List<Book> books = new ArrayList<>(this.getAllBooks());
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(deleteIsbn)) {
                books.remove(books.get(i));
                break;
            }
        }
        try {
            overwriteBooks(books);
        } catch (IOException e) {
            System.out.println("Ghi file thất bại! Lỗi: " + e);
        }
    }

    public Book findByISBN(String isbn){
        return this.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }
    public List<Book> findByName(String name){
        return list(name, null);
    }

    public int updateTotalByIsbn(String isbn, int amount) throws IOException {
        List<Book> books = new ArrayList<>(this.getAllBooks());
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                int newTotal = books.get(i).getTotal() + amount;
                books.get(i).setTotal(newTotal);
                overwriteBooks(books);
                return newTotal;
            }
        }
        return -1; // Trả về -1 nếu không tìm thấy sách với ISBN đã cho
    }

    private static void overwriteBooks(List<Book> books) throws IOException {
        Path path = Path.of("src/test/data/book.csv");
        StringBuilder sb = new StringBuilder();

        for (Book book : books) {
            sb.append("\n").append(book.getIsbn()).append("|")
                    .append(book.getName()).append("|")
                    .append(book.getAuthor()).append("|")
                    .append(book.getPublisher()).append("|")
                    .append(book.getPublishYear()).append("|")
                    .append(book.getGenre()).append("|")
                    .append(book.getPrice()).append("|")
                    .append(book.getTotal()).append("|")
                    .append("\n");
        }

        try {
            Files.writeString(path, sb.toString(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Lỗi khi ghi file sách");
        }
        removeEmptyLines("src/test/data/book.csv");
    }

    private static String removeAccent(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            char baseChar = switch (c) {
                case 'á', 'à', 'ả', 'ã', 'ạ', 'ă', 'ắ', 'ằ', 'ẳ', 'ẵ', 'ặ', 'â', 'ấ', 'ầ', 'ẩ', 'ẫ', 'ậ' -> 'a';
                case 'Á', 'À', 'Ả', 'Ã', 'Ạ', 'Ă', 'Ắ', 'Ằ', 'Ẳ', 'Ẵ', 'Ặ', 'Â', 'Ấ', 'Ầ', 'Ẩ', 'Ẫ', 'Ậ' -> 'A';

                case 'é', 'è', 'ẻ', 'ẽ', 'ẹ', 'ê', 'ế', 'ề', 'ể', 'ễ', 'ệ' -> 'e';
                case 'É', 'È', 'Ẻ', 'Ẽ', 'Ẹ', 'Ê', 'Ế', 'Ề', 'Ể', 'Ễ', 'Ệ' -> 'E';

                case 'í', 'ì', 'ỉ', 'ĩ', 'ị' -> 'i';
                case 'Í', 'Ì', 'Ỉ', 'Ĩ', 'Ị' -> 'I';

                case 'ó', 'ò', 'ỏ', 'õ', 'ọ', 'ô', 'ố', 'ồ', 'ổ', 'ỗ', 'ộ', 'ơ', 'ớ', 'ờ', 'ở', 'ỡ', 'ợ' -> 'o';
                case 'Ó', 'Ò', 'Ỏ', 'Õ', 'Ọ', 'Ô', 'Ố', 'Ồ', 'Ổ', 'Ỗ', 'Ộ', 'Ơ', 'Ớ', 'Ờ', 'Ở', 'Ỡ', 'Ợ' -> 'O';

                case 'ú', 'ù', 'ủ', 'ũ', 'ụ', 'ư', 'ứ', 'ừ', 'ử', 'ữ', 'ự' -> 'u';
                case 'Ú', 'Ù', 'Ủ', 'Ũ', 'Ụ', 'Ư', 'Ứ', 'Ừ', 'Ử', 'Ữ', 'Ự' -> 'U';

                case 'ý', 'ỳ', 'ỷ', 'ỹ', 'ỵ' -> 'y';
                case 'Ý', 'Ỳ', 'Ỷ', 'Ỹ', 'Ỵ' -> 'Y';

                case 'đ' -> 'd';
                case 'Đ' -> 'D';

                default -> c;
            };

            sb.append(baseChar);
        }

        return sb.toString();
    }

    /**
     * 6.2 Thống kê số lượng sách
     * @param name
     * @param genre
     * @return List
     */
    public List<Book> list(String name, Genre genre){
        // TODO: sửa List
        try {
            Stream<Book> stream = this.stream();

            if (name != null) {
                String nameLowerCase = name.toLowerCase().trim();
                stream = stream.filter(java.util.Objects::nonNull)
                        .filter(book -> book.getName() != null)
                        .filter(book ->
                                book.getName().toLowerCase().trim().contains(nameLowerCase)
                                        || removeAccent(book.getName()).toLowerCase().trim().contains(nameLowerCase)
                        );
            }
            if (genre != null) {
                stream = stream.filter(book -> book.getGenre().equals(genre));
            }

            return stream.toList();
        } catch (NullPointerException e){
            System.out.println("Không có data thỏa điều kiện");
            return List.of();
        }
    }
}