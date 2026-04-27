package main.service;

import main.constants.AuthorConstants;
import main.info.user.User;
import main.repositories.UserRepository;

import java.io.IOException;
import java.util.Scanner;

public class AuthenService {

    private UserRepository userRepository = new UserRepository();
    public String USER_ID = "0";
    Scanner input = new Scanner(System.in);



    public User loginService(String userName, String password) {
        User foundedUser = null;
        try {
            foundedUser = userRepository.findByUserName(userName);
        } catch (IOException e) {
            System.out.println("Lỗi khi tìm kiếm người dùng: " + e.getMessage());
            return null;
        }
        if(foundedUser != null && checkLogin(foundedUser, userName, password)){
            USER_ID = foundedUser.getUserId();
            return foundedUser;
        } else {
            System.out.println("Đăng nhập thất bại!");
            return null;
        }
    }

    public User findUser(String userNameInput)  {
        User foundedUser;
        try{
            foundedUser = userRepository.findByUserName(userNameInput);
        } catch (IOException e) {
            System.out.println("Lỗi khi lấy danh sách người dùng: " + e.getMessage());
            return null;
        }
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
            USER_ID = AuthorConstants.DEFAULT_USER;
            return true;
        } else {
            return false;
        }
    }

}
