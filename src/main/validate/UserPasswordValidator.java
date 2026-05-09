package main.validate;

import main.constants.AuthorConstants;
import main.constants.ErrConstants;

import java.util.ArrayList;
import java.util.List;

public class UserPasswordValidator implements Validator<String> {
    @Override
    public List<String> validate(String target) {
        List<String> resultCheck = new ArrayList<>();
        if(target == null || target.isEmpty()) {
            resultCheck.add(ErrConstants.PASSWORD_CAN_NOT_NULL);
        }
        if (target != null && target.length() < 8) {
            resultCheck.add(ErrConstants.PASSWORD_MUSTBE_EXACTLY_LENGTH);
        }
        if (!containsSpecialChar(target)) {
            resultCheck.add(ErrConstants.PASSWORD_NEEDTO_USE_SPECIAL_KEY);
        }
        if (target.chars().filter(Character::isDigit).findAny().isEmpty()) {
            resultCheck.add(ErrConstants.PASSWORD_NEEDTO_USE_NUMBER);
        }
        if (target.chars().filter(Character::isUpperCase).findAny().isEmpty()) {
            resultCheck.add(ErrConstants.PASSWORD_NEEDTO_USE_UPPERCASE);
        }
        if (target.chars().filter(Character::isLowerCase).findAny().isEmpty()) {
            resultCheck.add(ErrConstants.PASSWORD_NEEDTO_USE_LOWERCASE);
        }
        if (target.contains(" ")) {
            resultCheck.add(ErrConstants.PASSWORD_CANT_USE_SPACE);
        }


        return resultCheck;
    }

    private static boolean containsSpecialChar(String input) throws IllegalArgumentException{
        if (input == null){
            throw new NullPointerException("Nội dung nhập không được để trống!");
        }
        boolean isSpecialChar = false;
        for (String eachChar : AuthorConstants.SPECIAL_CHAR_LIST){
            if (input.contains(eachChar)){
                isSpecialChar = true;
                System.out.println("Nội dung nhập không đươc bao gồm các ký tự đặc biệt  {\"!\",\"@\",\"#\",\"$\",\"%\",\"&\",\"*\",\"?\"}");
                break;
            }
        }
        return isSpecialChar;
    }

}
