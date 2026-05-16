package main.validate;

import main.vo.UserDetailVO;

import java.util.ArrayList;
import java.util.List;

public class UserCreateDataValidator implements Validator<UserDetailVO> {
    private final UserPasswordValidator passwordValidator = new UserPasswordValidator();

    @Override
    public List<String> validate(UserDetailVO target)  {
        List<String> resultCheck = new ArrayList<>();
        if (null != passwordValidator.validate(target.getPassword())) {
            resultCheck.addAll(passwordValidator.validate(target.getPassword()));
        }
        return resultCheck;
    }
}
