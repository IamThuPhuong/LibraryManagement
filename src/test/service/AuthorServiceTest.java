package test.service;

import main.enums.Gender;
import main.info.user.User;
import main.repositories.UserRepository;
import main.service.AuthenService;
import main.service.UserService;
import main.vo.UserDetailVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorServiceTest {
    public static Scanner input = new Scanner(System.in);
    public static User currentUser = new User();

    public User loginLibrary() {
        System.out.println("Đăng nhập vào thư viện:");
        System.out.println("Username:");
        String username = input.nextLine();
        System.out.println("Password");
        String password = input.nextLine();

        AuthenService authenService = new AuthenService();
        User user = authenService.loginService(username, password);
        currentUser = user;
        return user;
    }
}