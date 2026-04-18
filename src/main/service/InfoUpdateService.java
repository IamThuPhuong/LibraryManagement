package main.service;

import main.constants.AuthorConstants;
import main.info.user.User;
import main.vo.UserDetailVO;

import java.util.ArrayList;
import java.util.List;

public class InfoUpdateService {
    public User updateUser(User user, UserDetailVO vo){
        if(!vo.getUserName().isEmpty()){
            user.setUserName(vo.getUserName());
        }
        if (!vo.getFullName().isEmpty()){
            user.setFullName(vo.getFullName());
        }
        // password khong duoc nhap
        if (!vo.getBirthDay().equals(user.getBirthDay())){
            user.setBirthDay(vo.getBirthDay());
        }
        if (!vo.getAddress().isEmpty()){
            user.setAddress(vo.getAddress());
        }
        if (!vo.getGender().equals(user.getGender())){
            user.setGender(vo.getGender());
        }
        // status khong duoc sua
        return user;
    }

    public User createUser(List<User> userList, UserDetailVO vo) throws ExceptionInInitializerError{
        // Thay the SQL
        List<String> userIDList = new ArrayList<>();
        List<String> userNameList = new ArrayList<>();
        for (User user : userList){
            userIDList.add(user.getUserId());
            userNameList.add(user.getUserName());
        }

        // Create new user
        // new UserID
        User newUser = new User();
        do {
            int length = 6; // Số 6 chữ số
            int min = (int) Math.pow(10, length - 1);
            int max = (int) Math.pow(10, length) - 1;
            int randomNumber = (int)(Math.random() * ((max - min) + 1)) + min;

            newUser.setUserId(String.valueOf(randomNumber));
        } while (userIDList.contains(newUser.getUserId()));

        // new Username
        if (!userNameList.contains(vo.getUserName())){
            newUser.setUserName(vo.getUserName());
        }

        // new Password
        boolean isSpecialChar = false;
        for (String eachChar : AuthorConstants.SPECIAL_CHAR_LIST){
            if (vo.getUserName().contains(eachChar)){
                isSpecialChar = true;
                break; // ->reviewed
            }
        }
        if (!(vo.getUserName().isEmpty() && vo.getUserName().isBlank() && isSpecialChar)){
            newUser.setPassword(vo.getPassword());
        }

        // TODO: Làm tiếp khúc này nhe

        return newUser;
    }
}
