package main.validate;

import main.constants.ErrConstants;
import main.vo.UserChangePasswordVO;

import java.util.ArrayList;
import java.util.List;

public class UserChangePasswordDataValidator implements Validator<UserChangePasswordVO> {
    private final UserPasswordValidator passwordValidator;

    public UserChangePasswordDataValidator() {
        this.passwordValidator = new UserPasswordValidator();
    }

    @Override
    public List<String> validate(UserChangePasswordVO target) {
        // Rule kiểm tra: kiểm tra input trước nghiệp vụ sau
        List<String> resultCheck = new ArrayList<>();

        resultCheck.addAll(passwordValidator.validate(target.getOldPassword()));

        resultCheck.addAll(passwordValidator.validate(target.getNewPassword()));

        return resultCheck;
    }


}
