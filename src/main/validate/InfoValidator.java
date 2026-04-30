package main.validate;

import main.constants.AuthorConstants;
import main.info.user.User;
import main.repositories.UserRepository;
import main.vo.UserDetailVO;

import java.io.IOException;
import java.time.LocalDate;
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
 * @see UserDetailVO
 * @see User
 */
public class InfoValidator implements Validator {
    private List<User> userList;
    private UserRepository userRepository = new UserRepository();

    /**
     * Constructor khởi tạo InfoValidator với danh sách người dùng hiện có.
     *
     * @param userList danh sách {@link User} để kiểm tra trùng lặp username
     */
    public InfoValidator(List<User> userList) {
        this.userList = userList;
    }

    /**
     * Validate thông tin người dùng từ {@link UserDetailVO}.
     * Kiểm tra từng field: username (độ dài, ký tự), password (độ dài), ngày sinh (không được tương lai).
     *
     * <p><b>Lưu ý:</b> Phương thức này chưa throw exception, chỉ in lỗi ra console.
     * Khi chuyển sang Spring, nên cải thiện bằng cách collect lỗi vào list và return.
     *
     * @param target đối tượng {@link UserDetailVO} cần validate
     * @throws ClassCastException nếu target không phải {@link UserDetailVO}
     * @see #isValidLength(String)
     * @see #containsSpecialChar(String)
     * @see #isBirthdayInFuture(LocalDate)
     */
    public void validate(Object target) {
        UserDetailVO obj = (UserDetailVO) target;

        // Validate UserName
        if (obj.getUserName() == null) {
            System.out.println("UserName không được null!");
        } else {
            // Check ký tự đặc biệt
            if (containsSpecialChar(obj.getUserName())) {
                System.out.println("UserName không được chứa ký tự đặc biệt!");
            }
            // Check độ dài
            if (!isValidLength(obj.getUserName())) {
                System.out.println("UserName vượt quá độ dài cho phép (" + AuthorConstants.USER_MAX_LENGTH + " ký tự)");
            }
        }

        // Check trùng lặp username
        try {
            if (userRepository.getAllUserNames().contains(obj.getUserName())) {
                throw new IllegalArgumentException("Username đã tồn tại!");
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi lấy danh sách người dùng: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Validate Password
        if (obj.getPassword() == null) {
            System.out.println("Password không được null!");
        } else {
            if (containsSpecialChar(obj.getPassword())) {
                System.out.println("Password không được chứa ký tự đặc biệt!");
            }
            if (!isValidLength(obj.getPassword())) {
                System.out.println("Password vượt quá độ dài cho phép!");
            }
        }

        // Validate BirthDay
        if (isBirthdayInFuture(obj.getBirthDay())) {
            System.out.println("Ngày sinh không được lớn hơn ngày hôm nay!");
        }
    }

    /**
     * Kiểm tra độ dài của chuỗi input có hợp lệ không.
     * Hợp lệ nếu độ dài nhỏ hơn hoặc bằng {@link AuthorConstants#USER_MAX_LENGTH}.
     *
     * @param input chuỗi cần kiểm tra
     * @return {@code true} nếu độ dài hợp lệ, {@code false} nếu vượt quá hoặc null
     * @see AuthorConstants#USER_MAX_LENGTH
     */
    private boolean isValidLength(String input){
        if (input.length() < AuthorConstants.USER_MAX_LENGTH){
            return true;
        }
        return false;
    }

    /**
     * Kiểm tra chuỗi có chứa ký tự đặc biệt không.
     * Danh sách ký tự đặc biệt được định nghĩa trong {@link AuthorConstants#SPECIAL_CHAR_LIST}.
     *
     * <p>Ký tự cấm: {@code ! @ # $ % & * ?}
     *
     * @param input chuỗi cần kiểm tra
     * @return {@code true} nếu chứa ký tự đặc biệt, {@code false} nếu không
     * @throws NullPointerException nếu input là {@code null}
     * @see AuthorConstants#SPECIAL_CHAR_LIST
     */
    private static boolean containsSpecialChar(String input) throws IllegalArgumentException{
        if (input == null){
            throw new NullPointerException("Nội dung nhập không được để trống!");
        }
        boolean isSpecialChar = false;
        for (String eachChar : AuthorConstants.SPECIAL_CHAR_LIST){
            if (input.contains(eachChar)){
                isSpecialChar = true;
                System.out.println("Nội dung nhập không đươc bao gồm các ký tự đặc biệt  {\"!\",\"@\",\"#\",\"$\",\"%\",\"&\",\"*\",\"?\"}");
                break;
            }
        }
        return isSpecialChar;
    }

    /**
     * Kiểm tra ngày sinh có lớn hơn ngày hôm nay không.
     * Ngày sinh không được là ngày tương lai.
     *
     * <p>Ví dụ:
     * <pre>
     *   isBirthdayInFuture(LocalDate.now().plusDays(1)) // true (không hợp lệ)
     *   isBirthdayInFuture(LocalDate.now().minusYears(20)) // false (hợp lệ)
     * </pre>
     *
     * @param birthDay ngày sinh cần kiểm tra
     * @return {@code true} nếu ngày sinh trong tương lai (không hợp lệ), {@code false} nếu hợp lệ
     */
    private boolean isBirthdayInFuture(LocalDate birthDay) {
        return birthDay.isAfter(LocalDate.now());
    }
}


