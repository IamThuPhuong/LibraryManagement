package main.validate;

import main.constants.ErrConstants;
import main.vo.ReaderDetailVO;

import java.util.ArrayList;
import java.util.List;

public class ReaderUpdateDataValidator implements Validator<ReaderDetailVO>{

    @Override
    public List<String> validate(ReaderDetailVO target) {
        List<String> resultCheck = new ArrayList<>();
        // TODO: Viết tiếp check null
        return resultCheck;
    }
}
