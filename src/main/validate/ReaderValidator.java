package main.validate;

import main.constants.ErrConstants;
import main.repositories.ReaderRepository;
import main.vo.ReaderDetailVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReaderValidator implements Validator<ReaderDetailVO>{
    ReaderRepository readerRepository = new ReaderRepository();

    @Override
    public List<String> validate(ReaderDetailVO target) {
        List<String> errList = new ArrayList<>();

        if (target.getFullName() == null) {
            errList.add(ErrConstants.FULLNAME_CAN_NOT_NULL);
        }

        if (target.getIdCard() == null) {
            errList.add(ErrConstants.IDCARDNO_CAN_NOT_NULL);
        }

        if (readerRepository.findByIdCardNo(target.getIdCard()) != null){
            errList.add(ErrConstants.IDCARDNO_EXISTED);
        }

        if (isBirthdayInFuture(target.getBirthDate())) {
            System.out.println("Ngày sinh không được lớn hơn ngày hôm nay!");
            errList.add(ErrConstants.BIRTHDAY_AFTER_TODAY);
        }
        return errList;
    }

    private boolean isBirthdayInFuture(LocalDate birthDay) {
        return birthDay.isAfter(LocalDate.now());
    }
}
