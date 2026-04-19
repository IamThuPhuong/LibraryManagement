package main.service;

import main.constants.AuthorConstants;
import main.info.user.Gender;
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

        // new Username: không nằm trong List đã có && không chứa ký tự đặc biệt
        if (! (userNameList.contains(vo.getUserName()) && isSpecialChar(vo.getPassword())) ){
            newUser.setUserName(vo.getUserName());
        }

        // new Password: không trống && không chứa ký tự đặc biệt
        if (!(vo.getPassword().isEmpty() && vo.getPassword().isBlank() && isSpecialChar(vo.getPassword()))){
            newUser.setPassword(vo.getPassword());
        }

        // new FullName
        if (!vo.getFullName().isEmpty()){
            newUser.setFullName(vo.getFullName());
        }
        // new BirthDay
        if (!vo.getBirthDay().equals(AuthorConstants.INIT_DATE)){
            newUser.setBirthDay(vo.getBirthDay());
        }
        
        //new IdCard
        if(!vo.getIdCard().equals(AuthorConstants.INIT_STRING)){
            newUser.setIdCard(vo.getIdCard());
        }
        
        // new Address
        if(!vo.getAddress().equals(AuthorConstants.INIT_STRING)){
            newUser.setAddress(vo.getAddress());
        }
        // new Gender
        if(!vo.getGender().equals(Gender.OTHER)){
            newUser.setGender(vo.getGender());
        }

        return newUser;
    }

    private static boolean isSpecialChar(String wordToCheckSpecial) {
        boolean isSpecialChar = false;
        for (String eachChar : AuthorConstants.SPECIAL_CHAR_LIST){
            if (wordToCheckSpecial.contains(eachChar)){
                isSpecialChar = true;
                System.out.println("Nội dung nhập không đươc bao gồm các ký tự đặc biệt  {\"!\",\"@\",\"#\",\"$\",\"%\",\"&\",\"*\",\"?\"}");
                break;
            }
        }
        return isSpecialChar;
    }
}
