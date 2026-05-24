package main.validate;

import main.constants.ErrConstants;
import main.constants.UserConstants;
import main.entity.User;
import main.repository.UserRepository;
import main.vo.UserVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp InfoValidator dùng để validate thông tin người dùng (UserDetailVO).
 * Kiểm tra các constraints như độ dài, ký tự đặc biệt, ngày sinh, v.v.
 *
 * <p>Ví dụ sử dụng:
 * <pre>
 *   List&lt;User&gt; userList = new ArrayList&lt;&gt;();
 *   InfoValidator validator = new InfoValidator(userList);
 *   validator.validate(userDetailVO);
 * </pre>
 *
 * @author Thu Phương
 * @version 1.0
 * @since 2026-04-19
 * @see UserVO
 * @see User
 */
public class UserValidator implements Validator<UserVO> {
    private final UserRepository userRepository = new UserRepository();

    /**
     * Constructor khởi tạo InfoValidator với danh sách người dùng hiện có.
     */
    public UserValidator() {}

    /**
     * Validate thông tin người dùng từ {@link UserVO}.
     * Kiểm tra từng field: username (độ dài, ký tự), password (độ dài), ngày sinh (không được tương lai).
     *
     * <p><b>Lưu ý:</b> Phương thức này chưa throw exception, chỉ in lỗi ra console.
     * Khi chuyển sang Spring, nên cải thiện bằng cách collect lỗi vào list và return.
     *
     * @param target đối tượng {@link UserVO} cần validate
     * @throws ClassCastException nếu target không phải {@link UserVO}
     * @see #isValidLength(String)
     * @see #containsSpecialChar(String)
     * @see #isBirthdayInFuture(LocalDate)
     */
    @Override
    public List<String> validate(UserVO target) {
        // UserDetailVO obj = (UserDetailVO) target; ==> dùng cách này cast Object thì khi compile không lỗi nhưng khi người dùng chạy sẽ lỗi
        // Dùng generic để đảm bảo type safety, tránh lỗi ClassCastException khi cast Object.
        // Có gì báo lỗi trên compiler luôn

        UserPasswordValidator userPasswordValidator = new UserPasswordValidator();

        List<String> resultCheck = new ArrayList<>();
        resultCheck.addAll(userPasswordValidator.validate(target.getPassword()));

        // Validate UserName
        if (target.getUserName() == null) {
            System.out.println("UserName không được null!");
            resultCheck.add(ErrConstants.USERNAME_CAN_NOT_NULL);
        } else {
            // Check ký tự đặc biệt
            if (containsSpecialChar(target.getUserName())) {
                System.out.println("UserName không được chứa ký tự đặc biệt!");
                resultCheck.add(ErrConstants.USERNAME_CANT_USE_SPECIAL_KEY);
            }
            // Check độ dài
            if (!isValidLength(target.getUserName())) {
                System.out.println("UserName vượt quá độ dài cho phép (" + UserConstants.USER_MAX_LENGTH + " ký tự)");
                resultCheck.add(ErrConstants.USERNAME_MUSTBE_EXACTLY_LENGTH);
            }
        }
        // Validate Password
        if (target.getPassword() == null) {
            System.out.println("Password không được null!");
            resultCheck.add(ErrConstants.PASSWORD_CAN_NOT_NULL);
        } else {
            if (!containsSpecialChar(target.getPassword())) {
                System.out.println("Password phải chứa ít nhất 1 ký tự đặc biệt!");
                resultCheck.add(ErrConstants.PASSWORD_NEEDTO_USE_SPECIAL_KEY);
            }
        }

        // Check trùng lặp username
        if (userRepository.findByUserName(target.getUserName()) != null) {
            System.out.println("Tên người dùng đã tồn tại");
            resultCheck.add(ErrConstants.USERNAME_EXISTED);
        }

        // Validate BirthDay
        if (isBirthdayInFuture(target.getBirthDay())) {
            System.out.println("Ngày sinh không được lớn hơn ngày hôm nay!");
            resultCheck.add(ErrConstants.BIRTHDAY_AFTER_TODAY);
        }
        return resultCheck;
    }

    /**
     * Kiểm tra độ dài của chuỗi input có hợp lệ không.
     * Hợp lệ nếu độ dài nhỏ hơn hoặc bằng {@link UserConstants#USER_MAX_LENGTH}.
     *
     * @param input chuỗi cần kiểm tra
     * @return {@code true} nếu độ dài hợp lệ, {@code false} nếu vượt quá hoặc null
     * @see UserConstants#USER_MAX_LENGTH
     */
    private boolean isValidLength(String input){
        return input.length() < UserConstants.USER_MAX_LENGTH;
    }

    /**
     * Kiểm tra chuỗi có chứa ký tự đặc biệt không.
     * Danh sách ký tự đặc biệt được định nghĩa trong {@link UserConstants#SPECIAL_CHAR_LIST}.
     *
     * <p>Ký tự cấm: {@code ! @ # $ % & * ?}
     *
     * @param input chuỗi cần kiểm tra
     * @return {@code true} nếu chứa ký tự đặc biệt, {@code false} nếu không
     * @throws NullPointerException nếu input là {@code null}
     * @see UserConstants#SPECIAL_CHAR_LIST
     */
    private static boolean containsSpecialChar(String input) throws IllegalArgumentException{
        if (input == null){
            throw new NullPointerException("Nội dung nhập không được để trống!");
        }
        boolean isSpecialChar = false;
        for (String eachChar : UserConstants.SPECIAL_CHAR_LIST){
            if (input.contains(eachChar)){
                isSpecialChar = true;
            }
        }
        return isSpecialChar;
    }

    private boolean isBirthdayInFuture(LocalDate birthDay) {
        return birthDay.isAfter(LocalDate.now());
    }
}


