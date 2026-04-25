package main.validate;

import main.enums.Permission;
import main.info.user.User;
import main.service.AuthorService;

/**
 * Validator để kiểm tra quyền hạn (authorization) của người dùng.
 *
 * <p>Class này kiểm tra xem người dùng hiện tại có quyền thực hiện
 * hành động tương ứng với permission được chỉ định hay không.
 *
 * @author Thu Phương
 * @version 1.0
 * @since 2026-04-25
 */
public class AuthorValidator implements Validator {
    private User currentUser = new User();
    AuthorService authorService = new AuthorService();

    /**
     * Constructor khởi tạo AuthorValidator với user hiện tại và AuthorService.
     *
     * @param currentUser người dùng cần kiểm tra quyền (không được null)
     * @param authorService service để kiểm tra quyền
     */
    public AuthorValidator(User currentUser, AuthorService authorService) {
        this.currentUser = currentUser;
        this.authorService = authorService;
    }

    /**
     * Validate quyền của người dùng hiện tại.
     *
     * @param permission quyền cần kiểm tra
     * @throws IllegalArgumentException nếu currentUser hoặc permission null
     * @throws ExceptionInInitializerError nếu không có quyền
     */
    public void validate(Object permission){
        if (permission == null){
            throw new IllegalArgumentException("Lỗi phân quyền rồi sao permission null rồi???");
        }
        try{
            checkAssesible((Permission)permission);
        } catch (ExceptionInInitializerError e){
            System.out.println(e);
        }
    }

    /**
     * Kiểm tra quyền truy cập nội bộ.
     *
     * @param permission quyền cần kiểm tra
     * @throws ExceptionInInitializerError nếu không có quyền
     */
    private void checkAssesible(Permission permission){
        boolean isAccessable = authorService.checkPermission(currentUser, permission);
        if (!isAccessable){
            throw new ExceptionInInitializerError("Bạn không có quyền sử dụng tính năng này!");
        }
    }

}