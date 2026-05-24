package main.validate;

import main.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

public class UserCreateDataValidator implements Validator<UserVO> {
    private final UserPasswordValidator passwordValidator = new UserPasswordValidator();

    @Override
    public List<String> validate(UserVO target)  {
        List<String> resultCheck = new ArrayList<>();
        if (null != passwordValidator.validate(target.getPassword())) {
            resultCheck.addAll(passwordValidator.validate(target.getPassword()));
        }
        return resultCheck;
    }
}
