package main.validate;

import main.constants.ErrConstants;
import main.vo.UserDetailVO;

import java.util.ArrayList;
import java.util.List;

public class UserUpdateDataValidator implements Validator<UserDetailVO> {

    @Override
    public List<String> validate (UserDetailVO target){

        List<String> resultCheck = new ArrayList<>();
        if (target.getUserName() != null) {
            resultCheck.add(ErrConstants.USERNAME_CAN_NOT_CHANGE);
        }
        return resultCheck;
    }
}
