package main.service;

import main.constants.AuthorConstants;
import main.info.user.User;

import java.util.List;
import java.util.Scanner;

public class AuthorService {
    public String USER_ID = "0";
    Scanner input = new Scanner(System.in);


    public void loginService(List<User> users){
        boolean checkLogin = false;
        do{
            System.out.println("Username:");
            String userName = input.nextLine();
            System.out.println("Password:");
            String password = input.nextLine();

            AuthorService authorService = new AuthorService();
            User foundedUser = authorService.findUser(users, userName);
            checkLogin = checkLogin(foundedUser, userName, password);
        } while (!checkLogin);
    }



    public User findUser(List<User> listUser, String userNameInput){
        for(User user : listUser) {
            if (user.getUserName().equals(userNameInput)) {
                return user;
            }
        }
        System.out.println("Không tồn tại người dùng này!");
        return null;
    }

    public boolean checkLogin(User user, String userNameInput, String passwordInput){
        if(user.getUserName().equals(userNameInput) && user.getPassword().equals(passwordInput)){
            System.out.println("Đăng nhập thành công!");
            USER_ID = user.getUserId();
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
