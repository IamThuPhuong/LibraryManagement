package main.validate;

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
        if (target.length() < 8) {
            resultCheck.add(ErrConstants.PASSWORD_MUSTBE_EXACTLY_LENGTH);
        }

        return resultCheck;
    }


}
