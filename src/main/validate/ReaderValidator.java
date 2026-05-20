package main.validate;

import main.constants.ErrConstants;
import main.constants.ReaderConstants;
import main.constants.UserConstants;
import main.repository.ReaderRepository;
import main.vo.ReaderDetailVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ReaderValidator implements Validator<ReaderDetailVO>{
    ReaderRepository readerRepository = new ReaderRepository();

    @Override
    public List<String> validate(ReaderDetailVO target) {
        List<String> errList = new ArrayList<>();

        if (target.getFullName() == null || target.getFullName().isEmpty()) {
            errList.add(ErrConstants.FULLNAME_CAN_NOT_NULL);
        }

        if (target.getIdCard() == null || target.getIdCard().isEmpty()) {
            errList.add(ErrConstants.IDCARDNO_CAN_NOT_NULL);
        }

        if (readerRepository.findByIdCardNo(target.getIdCard()) != null){
            errList.add(ErrConstants.IDCARDNO_EXISTED);
        }

        if(containsSpecialChar(target.getIdCard())){
            errList.add(ErrConstants.IDCARDNO_CONTAINS_SPECIAL_KEYS);
        }

        if(containsLetter(target.getIdCard())){
            errList.add(ErrConstants.IDCARDNO_CONTAINS_LETTERS);
        }

        if(target.getIdCard().length() > ReaderConstants.IDCARDNO_LENGTH){
            errList.add(ErrConstants.IDCARD_LENGTH_INVALID);
        }

        if (isBirthdayInFuture(target.getBirthDate())) {
            System.out.println("Ngày sinh không được lớn hơn ngày hôm nay!");
            errList.add(ErrConstants.BIRTHDAY_AFTER_TODAY);
        }

        if(target.getEmail() != null || !target.getEmail().isEmpty()){
            if (!isValidMail(target.getEmail())){
                errList.add(ErrConstants.INVALID_MAIL);
            }
        }
        return errList;
    }

    private boolean isBirthdayInFuture(LocalDate birthDay) {
        return birthDay.isAfter(LocalDate.now());
    }

    private static boolean containsSpecialChar(String input) throws IllegalArgumentException{
        if (input == null){
            throw new NullPointerException("Nội dung nhập không được để trống!");
        }
        boolean isSpecialChar = false;
        for (String eachChar : UserConstants.SPECIAL_CHAR_LIST){
            if (input.contains(eachChar)){
                isSpecialChar = true;
                System.out.println("Nội dung nhập không đươc bao gồm các ký tự đặc biệt  {\"!\",\"@\",\"#\",\"$\",\"%\",\"&\",\"*\",\"?\"}");
                break;
            }
        }
        return isSpecialChar;
    }

    public static boolean containsLetter(String str) {
        if (str == null || str.isEmpty()){
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidMail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
}
