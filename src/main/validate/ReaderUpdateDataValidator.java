package main.validate;

import main.vo.ReaderVO;

import java.util.ArrayList;
import java.util.List;

public class ReaderUpdateDataValidator implements Validator<ReaderVO>{

    @Override
    public List<String> validate(ReaderVO target) {
        List<String> resultCheck = new ArrayList<>();
        // TODO: Viết tiếp check null
        return resultCheck;
    }
}
