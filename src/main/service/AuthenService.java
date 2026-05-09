package main.service;

import main.constants.AuthenConstants;
import main.constants.AuthorConstants;
import main.info.user.User;
import main.repositories.UserRepository;
import main.validate.UserChangePasswordDataValidator;
import main.validate.UserPasswordValidator;
import main.validate.Validator;
import main.vo.UserChangePasswordVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthenService {

    private final UserRepository userRepository = new UserRepository();
    public static String USER_ID = "0";
    Scanner input = new Scanner(System.in);

    /** Validate check password hợp lệ khi tạo mới user */
    Validator<String> passwordCreateValidator = new UserPasswordValidator();

    /** Validate check password hợp lệ khi cập nhật password */
    Validator<UserChangePasswordVO> passwordUpdateValidator = new UserChangePasswordDataValidator();


    public boolean loginService(String userName, String password) {
        User foundedUser;
        try {
            foundedUser = userRepository.findByUserName(userName);
        } catch (NullPointerException e) {
            System.out.println("Không tồn tại người dùng này!");
            return false;
        }
        if(foundedUser != null && checkLogin(foundedUser, userName, password)){
            USER_ID = foundedUser.getUserId();
            return true;
        } else {
            System.out.println("Đăng nhập thất bại!");
            return false;
        }
    }

    public User findUser(String userNameInput)  {
        User foundedUser;
        foundedUser = userRepository.findByUserName(userNameInput);
        if (foundedUser != null) {
            return foundedUser;
        }
        System.out.println("Không tồn tại người dùng này!");
        return null;
    }

    public boolean checkLogin(User user, String userNameInput, String passwordInput) {
        try {
            if (user == null) {
                throw new NullPointerException("Không tồn tại người dùng này!");
            }
        } catch (NullPointerException e) {
            System.out.println(e);
            return false;
        }
        if(user.getUserName().equals(userNameInput) && user.getPassword().equals(passwordInput)){
            System.out.println("Đăng nhập thành công!");
            return true;
        } else {
            System.out.println("Tên người dùng hoặc mật khẩu sai!");
            return false;
        }
    }

    public boolean logoutService(User user){
        System.out.println("Ban co chac muon dang xuat?:");
        System.out.println("\t1. Co");
        System.out.println("\t2. Khong.");
        Boolean answer = false;
        int userAnswer = input.nextInt();
        input.nextLine();
        if(AuthorConstants.LOUGOUT_FLAG == userAnswer){
            USER_ID = AuthenConstants.DEFAULT_USER;
            return true;
        } else {
            return false;
        }
    }

    public boolean changePassword(User user, UserChangePasswordVO changePasswordVO) throws IllegalArgumentException, IOException {
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
        userRepository.updateUser(user);
        System.out.println("Đổi mật khẩu thành công!");
        return true;
    }
}
