package test;

import main.info.user.User;
import main.repositories.UserRepository;
import main.service.AuthenService;
import test.service.AuthorServiceTest;
import test.service.UserServiceTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenuTest {
    public static Scanner input = new Scanner(System.in);
    public static User currentUser = new User();

    public static void main(String[] args) throws IOException {

        System.out.println("Chọn 1 chức năng:");
        System.out.println("1. Đăng nhập");
        //System.out.println("2. Đăng ký");

        String chosenService = input.nextLine();

        AuthorServiceTest authorServiceTest = new AuthorServiceTest();
        UserServiceTest userServiceTest = new UserServiceTest();
        switch (chosenService) {
            case "1":
                authorServiceTest.loginLibrary();
                break;
            case "2":
                userServiceTest.createUser();
                break;
        }

        if (currentUser != null) {
            System.out.println("Chọn chức năng");
            System.out.println("Quản lý người dùng (admin only - hiện tạm để test phân quyền)");
            System.out.println("1. Tạo người dùng");
            System.out.println("2. Cập nhật thông tin người dùng");
            System.out.println("Quản lý thông tin cá nhân");
            System.out.println("3. Cập nhật thông tin cá nhân");
            System.out.println("4. Đổi mật khẩu");
            System.out.println("9. Đăng xuất");

            chosenService = input.nextLine();

            AuthenService authenService = new AuthenService();
            UserRepository userRepository = new UserRepository();
            switch (chosenService) {
                case "1":
                    userServiceTest.createUser();
                case "2":
                    userRepository.getAllUsers().forEach(user -> System.out.println("Username: " + user.getUserName() + " - FullName: " + user.getFullName()));
                    System.out.println("Nhập username của người dùng muốn cập nhật:");
                    String username = input.nextLine();
                    User userToUpdate = authenService.findUser(username);
                    userServiceTest.updateUser(userToUpdate);
                case "3":
                    userServiceTest.updateUser(currentUser);
                case "4":
                    userServiceTest.changePassword(currentUser);
                case "9":
                    boolean checkLogout = authenService.logoutService(currentUser);
                    if (checkLogout) {
                        currentUser = null; // TODO - sau này sẽ xóa token client side, hoặc xóa session server side
                        System.out.println("Đăng xuất thành công!");
                    } else {
                        System.out.println("Đăng xuất thất bại!");
                    }
            }
        }

    }

}
