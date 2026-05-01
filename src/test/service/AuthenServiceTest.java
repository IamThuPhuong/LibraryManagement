package test.service;

import main.info.user.User;
import main.repositories.UserRepository;
import main.service.AuthenService;
import main.vo.UserChangePasswordVO;

import java.io.IOException;
import java.util.Scanner;

public class AuthenServiceTest {
    private final UserRepository userRepository = new UserRepository();
    public static Scanner input = new Scanner(System.in);

    public boolean loginLibrary() {
        System.out.println("Đăng nhập vào thư viện:");
        System.out.println("Username:");
        String username = input.nextLine();
        System.out.println("Password");
        String password = input.nextLine();

        AuthenService authenService = new AuthenService();
        return authenService.loginService(username, password);
    }

    public void changePassword(User user) {
        System.out.println("=========ĐỔI MẬT KHẨU===========");
        System.out.println("Nhập mật khẩu hiện tại:");
        String currentPassword = input.nextLine();
        while (!currentPassword.equals(user.getPassword())) {
            System.out.println("Mật khẩu hiện tại không đúng! Vui lòng nhập lại:");
            currentPassword = input.nextLine();
        }
        System.out.println("Nhập mật khẩu mới:");
        String newPassword = input.nextLine();
        while (newPassword.isEmpty()) {
            System.out.println("Mật khẩu không được để trống! Vui lòng nhập lại:");
            newPassword = input.nextLine();
        }
        // TODO: Làm tiếp phần đổi mật khẩu
        System.out.println("Xác nhận mật khẩu mới:");
        String comfirmPassword = input.nextLine();
        while (!comfirmPassword.equals(newPassword)) {
            System.out.println("Mật khẩu xác nhận không khớp! Vui lòng nhập lại:");
            comfirmPassword = input.nextLine();
        }

        UserChangePasswordVO changePasswordVO = new UserChangePasswordVO();
        changePasswordVO.setOldPassword(currentPassword);
        changePasswordVO.setNewPassword(newPassword);
        changePasswordVO.setConfirmPassword(comfirmPassword);

        AuthenService authenService = new AuthenService();
        try {
            boolean isChanged = authenService.changePassword(user, changePasswordVO);
            if (isChanged) {
                System.out.println("Đổi mật khẩu thành công!");
            } else {
                System.out.println("Đổi mật khẩu thất bại!");
            }
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("Lỗi khi đổi mật khẩu: " + e.getMessage());
        }

    }

}