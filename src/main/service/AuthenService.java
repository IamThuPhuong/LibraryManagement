package main.service;

import main.constants.AuthenConstants;
import main.entity.User;
import main.enums.Permission;
import main.repository.UserRepository;
import main.validate.AuthorValidator;
import main.validate.UserChangePasswordDataValidator;
import main.validate.Validator;
import main.vo.UserChangePasswordVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Chức năng 1: Người dùng khi muốn sử dụng các chức năng của hệ thống phải thực hiện đăng
 * nhập.
 *
 * @author Thu Phương
 * @version 1.0
 * @since 2026-04-10
 */
public class AuthenService {

    private final UserRepository userRepository = new UserRepository();
    public static String USER_ID = AuthenConstants.IS_NOT_LOGIN_FLAG;
    Scanner input = new Scanner(System.in);

    private final AuthorService authorService = new AuthorService();
    private User currentUser = null;
    Validator<Permission> authorValidator = new AuthorValidator();

    /** Validate check password hợp lệ khi cập nhật password */
    Validator<UserChangePasswordVO> passwordUpdateValidator = new UserChangePasswordDataValidator();


    /**
     * 1.1 Đăng nhập
     * @param userName
     * @param password
     * @return boolean
     */
    public boolean login(String userName, String password) {
        User foundedUser;
        try {
            foundedUser = userRepository.findByUserName(userName);
        } catch (NullPointerException e) {
            System.out.println("Không tồn tại người dùng này!");
            return false;
        }
        if(foundedUser != null){
            if(foundedUser.getPassword().equals(password)){
                USER_ID = foundedUser.getUserId();
                currentUser = userRepository.findByUserId(AuthenService.USER_ID);
                authorValidator = new AuthorValidator(currentUser, authorService);
                return true;
            } else {
                System.out.println("Đăng nhập thất bại!");
                return false;
            }
        } else {
            System.out.println("Đăng nhập thất bại!");
            return false;
        }
    }

    public User findUser(String userNameInput)  {
        authorValidator.validate(Permission.COMMON);
        User foundedUser;
        foundedUser = userRepository.findByUserName(userNameInput);
        if (foundedUser != null) {
            return foundedUser;
        }
        System.out.println("Không tồn tại người dùng này!");
        return null;
    }

    public boolean checkLogin() {
        return AuthenService.USER_ID != null;
    }

    /**
     * 1.2 Đăng xuất
     * @return boolean
     */
    public boolean logout(){
        authorValidator.validate(Permission.COMMON);
        System.out.println("Ban co chac muon dang xuat?:");
        System.out.println("\t1. Co");
        System.out.println("\t2. Khong.");
        int userAnswer = input.nextInt();
        input.nextLine();
        if(AuthenConstants.LOUGOUT_FLAG == userAnswer){
            USER_ID = AuthenConstants.IS_NOT_LOGIN_FLAG;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 1.3 Đổi mật khẩu
     * @param user
     * @param changePasswordVO
     * @return boolean
     */
    public boolean changePassword(User user, UserChangePasswordVO changePasswordVO) throws IllegalArgumentException, IOException {
        authorValidator.validate(Permission.COMMON);
        List<String> errorList = new ArrayList<>();
        errorList.addAll(passwordUpdateValidator.validate(changePasswordVO));
        if (!errorList.isEmpty()) {
            System.out.println("Không thể đổi mật khẩu do có lỗi sau:");
            for (String error : errorList) {
                System.out.println("- " + error);
            }
            return false;
        }
        String newPass = changePasswordVO.getNewPassword();
        String confirmPass = changePasswordVO.getNewPassword();
        if (!newPass.equals(confirmPass)) {
            System.out.println("Mật khẩu xác nhận không khớp!");
            return false;
        }
        user.setPassword(newPass);
        userRepository.update(user);
        System.out.println("Đổi mật khẩu thành công!");
        return true;
    }
}
