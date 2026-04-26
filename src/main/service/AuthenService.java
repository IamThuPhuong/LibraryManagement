package main.service;

import main.constants.AuthorConstants;
import main.info.user.User;

import java.util.List;
import java.util.Scanner;

public class AuthenService {
    public String USER_ID = "0";
    public String token = "";
    Scanner input = new Scanner(System.in);


    public User loginService(List<User> users, String userName, String password){
        boolean checkLogin = false;
        AuthenService authenService = new AuthenService();
        User foundedUser = authenService.findUser(users, userName);
        checkLogin = checkLogin(foundedUser, userName, password);

        if(checkLogin){
            USER_ID = foundedUser.getUserId();
            return foundedUser;
        } else {
            return null;
        }
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

    public boolean checkLogin(User user, String userNameInput, String passwordInput) throws NullPointerException {
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
