package main.validate;

import main.constants.AuthorConstants;
import main.enums.Permission;
import main.entity.User;
import main.repository.UserRepository;
import main.service.AuthorService;

import java.util.ArrayList;
import java.util.List;

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
public class AuthorValidator implements Validator<Permission> {
    private final UserRepository userRepository = new UserRepository();
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

    public AuthorValidator() {

    }

    /**
     * Validate quyền của người dùng hiện tại.
     *
     * @param permission quyền cần kiểm tra
     */
    @Override
    public List<String> validate(Permission permission){
        List<String> resultCheck = new ArrayList<>();
        if (permission == null) {
                resultCheck.add(AuthorConstants.PERMISSION_NULL);
                throw new IllegalArgumentException(AuthorConstants.PERMISSION_NULL);
        }
        if (!checkAssesible(permission)) {
            resultCheck.add(AuthorConstants.PERMISSION_DENIED);
            throw new ExceptionInInitializerError(AuthorConstants.PERMISSION_DENIED);
        }
        return resultCheck;
    }

    /**
     * Kiểm tra quyền truy cập nội bộ.
     *
     * @param permission quyền cần kiểm tra
     */
    private boolean checkAssesible(Permission permission){
        boolean isAccessable = false;
        try{
            isAccessable = authorService.checkPermission(currentUser, permission);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
            return false;
        }

        if (!isAccessable){
            return false;
        }
        return true;
    }

}