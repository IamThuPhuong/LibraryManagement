package main.service;

import main.entity.User;
import main.enums.Gender;
import main.enums.Genre;
import main.enums.Permission;
import main.repository.BookRepository;
import main.repository.BorrowDetailRepository;
import main.repository.ReaderRepository;
import main.validate.AuthorValidator;
import main.validate.Validator;

import static test.MainMenuTest.userRepository;

public class DashboardService {
    private final BookRepository bookRepository = new BookRepository();
    private final ReaderRepository readerRepository = new ReaderRepository();
    private final BorrowDetailRepository borrowDetailRepository = new BorrowDetailRepository();
    /** Phân quyền: Service xử lý phân quyền */
    private final AuthorService authorService = new AuthorService();
    /** Phân quyền: instance variable: người dùng sau khi đăng nhập */
    private final User currentUser = userRepository.findByUserId(AuthenService.USER_ID);
    /** Phân quyền: Validate check quyền truy cập chức năng */
    Validator<Permission> authorValidator = new AuthorValidator(currentUser, authorService);

    /**
     * 6.1 Thống kê số lượng sách trong thư viện
     * @return
     */
    public int bookStats() {
        authorValidator.validate(Permission.STATISTIC);
        int sum = 0;
        for (int i = 0; i < bookRepository.getAll().size(); i++) {
            System.out.println("Sách thứ " + (i + 1) + ": " + bookRepository.getAll().get(i).getName() + " - số lượng" + bookRepository.getAll().get(i).getTotal());
            sum += bookRepository.getAll().get(i).getTotal();
        }
        return sum;
    }

    /**
     * 6.2 Thống kê số lượng sách theo thể loại
     * @param genre
     * @return
     */
    public int bookStatsByGenre(Genre genre) {
        authorValidator.validate(Permission.STATISTIC);
        int sum = 0;
        for (int i = 0; i < bookRepository.list(null, genre).size(); i++){
            System.out.println("Sách thứ " + (i + 1) + ": " + bookRepository.getAll().get(i).getName() + " - số lượng" + bookRepository.getAll().get(i).getTotal());
            sum += bookRepository.list(null, genre).get(i).getTotal();
        }
        return sum;
    }

    /**
     * 6.3 Thống kê số lượng độc giả
     * @return
     */
    public int readerStats(){
        authorValidator.validate(Permission.STATISTIC);
        return readerRepository.getAll().size();
    }

    /**
     * 6.4 Thống kê số lượng độc giả theo giới tính
     * @param gender
     * @return
     */
    public int readerStatsByGender(Gender gender){
        authorValidator.validate(Permission.STATISTIC);
        return readerRepository.list(null, gender).size();
    }

    /**
     * 6.5 Thống kê số sách đang được mượn
     * @return
     */
    public int borrowStats(){
        authorValidator.validate(Permission.COMMON);
        return borrowDetailRepository.list(null, Boolean.FALSE, null).size();
    }

    /**
     * 6.6 Thống kê danh sách độc giả bị trễ hạn
     * @return
     */
    public int lateReturnStats(){
        authorValidator.validate(Permission.COMMON);
        return borrowDetailRepository.list(null, null, Boolean.TRUE).size();
    }

}
