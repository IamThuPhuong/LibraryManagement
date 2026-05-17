package test.service;

import main.constants.Constants;
import main.enums.Genre;
import main.info.Book;
import main.service.BookService;
import main.vo.BookDetailVO;

import java.io.IOException;
import java.util.List;

import static test.service.AuthenServiceTest.input;

public class BookServiceTest {
    BookService bookService = new BookService();

    public void showBookList() {
        for (Book book : bookService.showBookList()) {
            System.out.println(book.toString());
        }
    }

    public void addBook() {
        // Form nhập thông tin sách
        BookDetailVO bookDetailVO = new BookDetailVO();
        System.out.println("Form nhập thông tin sách:");

        System.out.print("1. Mã ISBN (*):");
        String isbn = input.nextLine();
        while (isbn.isEmpty()) {
            System.out.println("Mã ISBN không được để trống! Vui lòng nhập lại:");
            isbn = input.nextLine();
        }

        System.out.print("\n2. Tên sách (*):");
        String name = input.nextLine();
        while (name.isEmpty()) {
            System.out.println("Tên sách không được để trống! Vui lòng nhập lại:");
            name = input.nextLine();
        }

        System.out.print("\n3. Tác giả:");
        String author = input.nextLine();

        System.out.print("\n4. Nhà xuất bản:");
        String publisher = input.nextLine();

        System.out.print("\n5. Năm xuất bản:");
        String publishYear = input.nextLine();

        System.out.print("\n6. Chọn thể loại:");
        Genre genreEnum = chooseGenre();

        System.out.print("\n7. Giá tiền:");
        String price = input.nextLine();

        System.out.print("\n8. Số lượng nhập kho:");
        String total = input.nextLine();

        // Set thông tin sách từ form vào BookDetailVO
        bookDetailVO.setIsbn(isbn);
        bookDetailVO.setName(name);
        bookDetailVO.setAuthor(author);
        bookDetailVO.setPublisher(publisher);
        bookDetailVO.setPublishYear(publishYear);
        bookDetailVO.setGenre(genreEnum);
        bookDetailVO.setPrice(price);
        bookDetailVO.setTotal(total);

        // Service
        bookService.create(bookDetailVO);
        System.out.println("==Kết thúc tiến trình thêm sách mới==!");
    }

    /**
     * Cập nhật sách phía màn hình
     */
    public void updateBook() throws IOException {
        while (true) {
            System.out.print("=========DANH SÁCH SÁCH HIỆN CÓ===========");
            List<Book> bookList = bookService.showBookList();
            for (int i = 0; i < bookList.size(); i++) {
                System.out.println(i + 1 + ". " + bookList.get(i).toString());
            }

            System.out.println("Chọn cuốn sách muốn update (Từ 1 đến " + bookList.size() + "). Chọn 0 để thoát.");
            int bookNo = input.nextInt();

            if (bookNo == Constants.INT_0) {
                input.nextLine();
                return;
            }

            input.nextLine();
            Book book = bookList.get(bookNo - 1);
            BookDetailVO vo = new BookDetailVO();
            String chosenInfo;

            do {
                System.out.println("=========THÔNG TIN SÁCH===========");
                System.out.println("1. Mã ISBN: " + book.getIsbn());
                System.out.println("2. Tên sách: " + book.getName());
                System.out.println("3. Tác giả: " + book.getAuthor());
                System.out.println("4. Nhà xuất bản: " + book.getPublisher());
                System.out.println("5. Năm xuất bản: " + book.getPublishYear());
                System.out.println("6. Thể loại: " + (book.getGenre() != null ? book.getGenre().getDisplayName() : "Khác"));
                System.out.println("7. Giá tiền: " + book.getPrice());
                System.out.println("8. Số lượng: " + book.getTotal());

                System.out.println("Chọn thông tin muốn cập nhật (1-8), 0 để hoàn tất update, 9 để quay lại, 99 để thoát hẳn:");
                chosenInfo = input.nextLine();

                switch (chosenInfo) {
                    case "1":
                        System.out.println("Nhập mã ISBN mới:");
                        String newIsbn = input.nextLine();
                        vo.setIsbn(newIsbn);
                        break;
                    case "2":
                        System.out.println("Nhập tên sách mới:");
                        String newName = input.nextLine();
                        vo.setName(newName);
                        break;
                    case "3":
                        System.out.println("Nhập tác giả mới:");
                        String newAuthor = input.nextLine();
                        vo.setAuthor(newAuthor);
                        break;
                    case "4":
                        System.out.println("Nhập nhà xuất bản mới:");
                        String newPublisher = input.nextLine();
                        vo.setPublisher(newPublisher);
                        break;
                    case "5":
                        System.out.println("Nhập năm xuất bản mới:");
                        String newPublishYear = input.nextLine();
                        vo.setPublishYear(newPublishYear);
                        break;
                    case "6":
                        System.out.println("Chọn thể loại mới:");
                        vo.setGenre(chooseGenre());
                        break;
                    case "7":
                        System.out.println("Nhập giá tiền mới:");
                        String newPrice = input.nextLine();
                        vo.setPrice(newPrice);
                        break;
                    case "8":
                        System.out.println("Nhập số lượng mới:");
                        String newTotal = input.nextLine();
                        vo.setTotal(newTotal);
                        break;
                }

            } while (!chosenInfo.equals("0") && !chosenInfo.equals("99") && !chosenInfo.equals("9"));

            if (chosenInfo.equals("9")) {
                continue;
            }

            if (chosenInfo.equals("99")) {
                break;
            }

            BookService bookServiceUpdate = new BookService();
            bookServiceUpdate.update(book, vo);

            System.out.println("Cập nhật thông tin sách thành công! Thông tin mới:");
            System.out.println("Mã ISBN: " + book.getIsbn());
            System.out.println("Tên sách: " + book.getName());
            System.out.println("Tác giả: " + book.getAuthor());
            System.out.println("Nhà xuất bản: " + book.getPublisher());
            System.out.println("Năm xuất bản: " + book.getPublishYear());
            System.out.println("Thể loại: " + (book.getGenre() != null ? book.getGenre().getDisplayName() : "Khác"));
            System.out.println("Giá tiền: " + book.getPrice());
            System.out.println("Số lượng: " + book.getTotal());
        }
    }

    public void findBookByName() {
        System.out.println("Nhập tên sách tìm kiếm:");
        String name = input.nextLine();
        List<Book> foundBooks = bookService.findByName(name);
        if (foundBooks == null || foundBooks.isEmpty()) {
            System.out.println("Không tìm thấy sách nào");
            return;
        }
        System.out.println("Kết quả tìm kiếm:");
        for (int i = 0; i < foundBooks.size(); i++) {
            System.out.println(i + ". " + foundBooks.get(i).toString());
        }
    }

    public void findBookByIsbn() {
        System.out.println("Nhập mã ISBN:");
        String isbn = input.nextLine();
        Book foundBook = bookService.findByISBN(isbn);
        if (foundBook == null) {
            System.out.println("Không tồn tại đầu sách này");
            return;
        }
        System.out.println("Kết quả tìm kiếm:");
        System.out.println(foundBook);
    }

    private Genre chooseGenre(){
        System.out.print("\n\t1. Tiểu thuyết");
        System.out.print("\n\t2. Kỹ năng sống");
        System.out.print("\n\t3. Văn học cổ điển");
        System.out.print("\n\t4. Khoa học - Công nghệ");
        System.out.print("\n\t5. Kinh tế - Kinh doanh");
        System.out.print("\n\t6. Lịch sử - Địa lý");
        System.out.print("\n\t7. Truyện thiếu nhi");
        System.out.print("\n\t8. Truyện tranh");
        System.out.print("\n\t9. Khác");
        String genre = input.nextLine();
        return switch (genre) {
            case "1" -> Genre.FICTION;
            case "2" -> Genre.SELF_HELP;
            case "3" -> Genre.CLASSIC_LITERATURE;
            case "4" -> Genre.SCIENCE_TECH;
            case "5" -> Genre.BUSINESS_ECONOMICS;
            case "6" -> Genre.HISTORY_GEOGRAPHY;
            case "7" -> Genre.CHILDREN;
            case "8" -> Genre.COMIC_MANGA;
            default -> Genre.OTHER;
        };
    }
}