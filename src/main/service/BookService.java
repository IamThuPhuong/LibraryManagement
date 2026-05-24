package main.service;

import main.constants.BookConstants;
import main.constants.Constants;
import main.enums.Genre;
import main.enums.Permission;
import main.entity.Book;
import main.entity.ReaderCard;
import main.entity.User;
import main.repository.BookRepository;
import main.validate.*;
import main.vo.BookVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static test.MainMenuTest.userRepository;

public class BookService {
    /**
     * Repository để thao tác với dữ liệu sách
     */
    BookRepository bookRepository = new BookRepository();

    /**
     * Service xử lý phân quyền
     */
    private static final AuthorService authorService = new AuthorService();

    /**
     * instance variable: người dùng sau khi đăng nhập
     */
    private User currentUser = userRepository.findByUserId(AuthenService.USER_ID);

    /**
     * Validate check quyền truy cập chức năng
     */
    Validator<Permission> authorValidator = new AuthorValidator(currentUser, authorService);

    /**
     * Validate đầu vào chức năng quản lý sách
     */
    Validator<BookVO> bookValidator = new BookValidator();

    /** Quyền truy cập chức năng */
    private static final Permission PERMISSION_OF_FUNCTION = Permission.MANAGE_USER;

    /**
     * 3.1 Xem danh sách các sách trong thư viện
     */
    public List<Book> showBookList() {
        authorValidator.validate(PERMISSION_OF_FUNCTION);
        return bookRepository.getAllBooks();
    }


    /**
     * 3.2 Thêm sách
     * @param vo
     * @return
     */
    public Book create(BookVO vo) {
        authorValidator.validate(PERMISSION_OF_FUNCTION);
        List<String> errorList = new ArrayList<>();
        errorList.addAll(bookValidator.validate(vo));

        if (!errorList.isEmpty()) {
            System.out.println("Không thể tạo mới sách do lỗi sau:");
            for (String error : errorList) {
                System.out.println("- " + error);
            }
            return null;
        }

        ReaderCard readerCard = new ReaderCard();
        Book book = new Book();

        if(!vo.getIsbn().equals(Constants.INIT_STRING)){
            book.setIsbn(vo.getIsbn());
        }
        if (!vo.getName().equals(Constants.INIT_STRING)){
            book.setName(vo.getName());
        }

        if(!vo.getAuthor().equals(Constants.INIT_STRING)){
            book.setAuthor(vo.getAuthor());
        }

        if(!vo.getPublisher().equals(Constants.INIT_STRING)){
            book.setPublisher(vo.getPublisher());
        }

        if(!vo.getPublishYear().equals(Constants.INIT_STRING)){
            book.setPublishYear(vo.getPublishYear());
        }

        if(!vo.getGenre().equals(Genre.OTHER)){
            book.setGenre(vo.getGenre());
        }

        if(!vo.getPrice().equals(Constants.DEFAULT_LONG)){
            book.setPrice(vo.getPrice());
        }

        if(!vo.getTotal().equals(Constants.INT_0)){
            book.setTotal(vo.getTotal());
        }

        bookRepository.saveBookToFile(book);

        return book;

    }

    /**
     * 3.3 Chỉnh sửa thông tin một quyển sách
     * @param book
     * @param vo
     * @return
     */
    public Book update(Book book, BookVO vo) throws IllegalArgumentException, ExceptionInInitializerError, IOException {
        authorValidator.validate(PERMISSION_OF_FUNCTION);
        List<String> errorList = new ArrayList<>(bookValidator.validate(vo));
        if (!errorList.isEmpty()){
            System.out.println("Không thể cập nhật sách do có lỗi sau:");
            for (String error : errorList) {
                System.out.println("- " + error);
            }
            return book;
        }

        if(!Constants.INIT_STRING.equals(vo.getName())) {
            book.setName(vo.getName());
        }

        if(!Constants.INIT_STRING.equals(vo.getAuthor())){
            book.setAuthor(vo.getAuthor());
        }

        if(!Constants.INIT_STRING.equals(vo.getPublisher())){
            book.setPublisher(vo.getPublisher());
        }

        if(!BookConstants.INIT_PUBLISH_YEAR.equals(vo.getPublishYear())){
            book.setPublishYear(vo.getPublishYear());
        }

        if(!Genre.OTHER.equals(vo.getGenre())){
            book.setGenre(vo.getGenre());
        }

        if(vo.getPrice().compareTo(Constants.DEFAULT_LONG) != Constants.INT_0){
            book.setPrice(vo.getPrice());
        }

        if(Constants.INT_0 != vo.getTotal()){
            book.setTotal(vo.getTotal());
        }

        bookRepository.update(book);

        return book;
    }

    /**
     * 3.4 Xóa thông tin sách
     * @param deleteId
     * @return
     */
    public String deleteBook(String deleteId){
        bookRepository.delete(deleteId);
        return deleteId;
    }

    /**
     * 3.5 Tìm kiếm sách theo ISBN
     * @param isbn
     * @return
     */
    public Book findByISBN(String isbn){
        return bookRepository.findByISBN(isbn);
    }

    /**
     * Tìm kiếm sách theo tên sách
     * @param name
     * @return
     */
    public List<Book> findByName(String name){
        return bookRepository.findByName(name);
    }
}
