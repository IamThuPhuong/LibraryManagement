package main.validate;

import main.constants.ErrConstants;
import main.vo.UserDetailVO;

import java.util.ArrayList;
import java.util.List;

public class UserCreateDataValidator implements Validator<UserDetailVO> {
    private final UserPasswordValidator passwordValidator = new UserPasswordValidator();

    @Override
    public List<String> validate(UserDetailVO target)  {
        List<String> resultCheck = new ArrayList<>();
        if (null != passwordValidator.validate(target.getPassword())) {
            resultCheck.add(ErrConstants.PASSWORD_INVALID);
        }
        return resultCheck;
    }
}
