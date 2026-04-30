package main.validate;

import main.constants.ErrConstants;
import main.info.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserCreateDataValidator implements Validator<User>{
    private final UserPasswordValidator passwordValidator = new UserPasswordValidator();

    @Override
    public List<String> validate(User target)  {
        List<String> resultCheck = new ArrayList<>();
        if (null != passwordValidator.validate(target.getPassword())) {
            resultCheck.add(ErrConstants.PASSWORD_CAN_NOT_NULL);
        }
        return resultCheck;
    }
}
