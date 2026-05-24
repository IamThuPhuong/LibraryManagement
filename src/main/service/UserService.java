package main.service;

import main.constants.Constants;
import main.enums.Gender;
import main.enums.Permission;
import main.enums.Status;
import main.enums.UserRole;
import main.entity.User;
import main.repository.UserRepository;
import main.validate.AuthorValidator;
import main.validate.UserCreateDataValidator;
import main.validate.UserValidator;
import main.validate.Validator;
import main.validate.UserUpdateDataValidator;
import main.vo.UserDetailVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Chức năng 1: Người dùng khi muốn sử dụng các chức năng của hệ thống phải thực hiện đăng
 * nhập.
 *
 * @author Thu Phương
 * @version 1.0
 * @since 2026-04-10
 */
public class UserService {

    /** Repository để thao tác với dữ liệu người dùng */
    UserRepository userRepository = new UserRepository();

    /** instance variable: người dùng sau khi đăng nhập */
    private User currentUser = userRepository.findByUserId(AuthenService.USER_ID);

    /** Common xử lý phân quyền */
    private static AuthorService authorService = new AuthorService();

    /** Quyền truy cập class InfoUpdateService */
    private static final Permission PERMISSION_OF_FUNCTION = Permission.MANAGE_USER;

    /** Validate check thông tin user hợp lệ */
    Validator<UserDetailVO> userValidator = new UserValidator();

    /** Validate check quyền truy cập chức năng */
    Validator<Permission> authorValidator = new AuthorValidator(currentUser, authorService);

    /** Validate check thông tin user hợp lệ khi tạo mới user */
    Validator<UserDetailVO> userCreateValidator = new UserCreateDataValidator();

    /** Validate check thông tin user hợp lệ khi cập nhật user */
    Validator<UserDetailVO> userUpdateValidator = new UserUpdateDataValidator();

    /**
     * 1.4 Cập nhật thông tin cá nhân
     * @param user
     * @param vo
     * @return user
     */
    public User updateUser(User user, UserDetailVO vo) throws IllegalArgumentException, ExceptionInInitializerError, IOException {
        authorValidator.validate(PERMISSION_OF_FUNCTION);
        List<String> errorList = new ArrayList<>(userUpdateValidator.validate(vo));
        if (!errorList.isEmpty()){
            System.out.println("Không thể cập nhật người dùng do có lỗi sau:");
            for (String error : errorList) {
                System.out.println("- " + error);
            }
            return user;
        }

        // Check xem sửa mình hay sửa người
        try {
            if (user == null) {
                throw new IllegalArgumentException("User cần cập nhật không tồn tại!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return user; // Trả về user gốc nếu có lỗi
        } catch (ExceptionInInitializerError e) {
            System.out.println(e.getMessage());
            return user; // Trả về user gốc nếu không có quyền
        }
        if(vo.getUserName() != null && !vo.getUserName().isEmpty()){
            user.setUserName(vo.getUserName());
        }
        if (vo.getFullName() != null && !vo.getFullName().isEmpty()){
            user.setFullName(vo.getFullName());
        }
        // password khong duoc nhap
        if (!vo.getBirthDay().equals(Constants.INIT_DATE)){
            user.setBirthDay(vo.getBirthDay());
        }
        if (vo.getAddress() != null && !vo.getAddress().isEmpty()){
            user.setAddress(vo.getAddress());
        }
        if (vo.getIdCard() != null && !vo.getIdCard().isEmpty()){
            user.setIdCard(vo.getIdCard());
        }
        if (!vo.getGender().equals(Gender.OTHER)){
            user.setGender(vo.getGender());
        }
        if(!vo.getStatus().equals(Status.ACTIVATED)){
            user.setStatus(Status.BLOCKED);
        }

        // Ghi đè file data.txt với nội dung mới từ userList sau khi đã cập nhật user
        userRepository.updateUser(user);

        return user;
    }

    /**
     * 1.5 Tạo người dùng
     * @param vo
     * @return User
     */
    public User createUser(UserDetailVO vo) throws ExceptionInInitializerError {
        authorValidator.validate(PERMISSION_OF_FUNCTION);

        List<String> errorList = new ArrayList<>();
        errorList.addAll(userCreateValidator.validate(vo));

        if (!errorList.isEmpty()){
            System.out.println("Không thể tạo người dùng mới do có lỗi sau:");
            for (String error : errorList) {
                System.out.println("- " + error);
            }
            return null;
        }

        User currentUser = userRepository.findByUserId(AuthenService.USER_ID);
        // Stream
        List<String> userNameList = userRepository.getAllUserNames();

        // validate input
        userValidator.validate(vo);
        if (currentUser != null){
            authorValidator.validate(PERMISSION_OF_FUNCTION);
        }

        // Create new user
        // new UserID: Sử dụng UUID (không cần kiểm tra trùng lặp vì xác suất trùng gần như 0)
        User newUser = new User();
        String userId = UUID.randomUUID().toString(); // Format: 550e8400-e29b-41d4-a716-446655440000
        newUser.setUserId(userId);

        // new Username: không nằm trong List đã có && không chứa ký tự đặc biệt
        if (!userNameList.contains(vo.getUserName())){
            newUser.setUserName(vo.getUserName());
        }

        // new Password: không trống && không chứa ký tự đặc biệt
        if (!vo.getPassword().isEmpty()){
            newUser.setPassword(vo.getPassword());
        }

        // new FullName
        if (!vo.getFullName().isEmpty()){
            newUser.setFullName(vo.getFullName());
        }
        // new BirthDay
        if (!vo.getBirthDay().equals(Constants.INIT_DATE)){
            newUser.setBirthDay(vo.getBirthDay());
        }
        
        //new IdCard
        if(!vo.getIdCard().equals(Constants.INIT_STRING)){
            newUser.setIdCard(vo.getIdCard());
        }
        
        // new Address
        if(!vo.getAddress().equals(Constants.INIT_STRING)){
            newUser.setAddress(vo.getAddress());
        }
        // new Gender
        if(!vo.getGender().equals(Gender.OTHER)){
            newUser.setGender(vo.getGender());
        }

        if (currentUser != null) {
            if (currentUser.getUserRole().equals(UserRole.ADMIN)) {
                newUser.setUserRole(vo.getUserRole());
            } else if (currentUser.getUserRole().equals(UserRole.MANAGER)) {
                if (vo.getUserRole().equals(UserRole.ADMIN)) {
                    System.out.println("Bạn không có quyền tạo người dùng có vai trò ADMIN!");
                    newUser.setUserRole(UserRole.OFFICER);
                } else {
                    newUser.setUserRole(vo.getUserRole());
                }
            }
        } else {
            newUser.setUserRole(UserRole.OFFICER);
        }


        // add vao data.csv sau khi chuyển sang Spring sẽ add vào DB
        userRepository.saveUserListToFile(newUser);

        return newUser;
    }
}
