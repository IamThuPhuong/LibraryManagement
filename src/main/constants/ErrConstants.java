package main.constants;

/**
 * Class chứa các hằng số định nghĩa mã lỗi cho hệ thống quản lý thư viện.
 * Các constant này được sử dụng trong validator và service để trả về mã lỗi chuẩn hóa.
 *
 * @author Thu Phương
 * @version 1.0
 * @since 2026-04-30
 */
public class ErrConstants {

    // ==================== USERNAME ERRORS ====================

    /** Username là trường không thể thay đổi sau khi tạo tài khoản để đảm bảo tính toàn vẹn dữ liệu. */
    public static final String USERNAME_CAN_NOT_CHANGE = "USERNAME_CAN_NOT_CHANGE";

    /** Username là trường bắt buộc phải có giá trị hợp lệ. */
    public static final String USERNAME_CAN_NOT_NULL = "USERNAME_CAN_NOT_NULL";

    /** Username chỉ được phép chứa chữ cái, số và dấu gạch dưới để đảm bảo an toàn và tương thích.*/
    public static final String USERNAME_CANT_USE_SPECIAL_KEY = "USERNAME_CANT_USE_SPECIAL_KEY";

    /** Username phải có độ dài từ 3 đến 20 ký tự (theo AuthorConstants.USER_MAX_LENGTH). */
    public static final String USERNAME_MUSTBE_EXACTLY_LENGTH = "USERNAME_MUSTBE_EXACTLY_LENGTH";

    /** Mỗi username phải là duy nhất để tránh xung đột. */
    public static final String USERNAME_EXISTED = "USERNAME_EXISTED";

    // ==================== PASSWORD ERRORS ====================

    /** Password là trường bắt buộc phải có giá trị để đảm bảo bảo mật. */
    public static final String PASSWORD_CAN_NOT_NULL = "PASSWORD_CAN_NOT_NULL";

    /** Password có thể chứa ký tự đặc biệt nhưng phải tuân thủ quy tắc bảo mật. */
    public static final String PASSWORD_CANT_USE_SPECIAL_KEY = "PASSWORD_CANT_USE_SPECIAL_KEY";

    /** Password phải có độ dài tối thiểu 8 ký tự để đảm bảo độ mạnh. */
    public static final String PASSWORD_MUSTBE_EXACTLY_LENGTH = "PASSWORD_MUSTBE_EXACTLY_LENGTH";

    // ==================== BIRTHDAY ERRORS ====================

    /** Ngày sinh không được là ngày trong tương lai để đảm bảo tính hợp lý của dữ liệu. */
    public static final String BIRTHDAY_AFTER_TODAY = "BIRTHDAY_AFTER_TODAY";

    // ==================== PERMISSION ERRORS ====================

    /** Permission phải được chỉ định rõ ràng để kiểm tra quyền hạn. */
    public static final String PERMISSION_NULL = "PERMISSION_NULL";

    /** Hệ thống từ chối truy cập do thiếu quyền hạn phù hợp. */
    public static final String PERMISSION_DENIED = "PERMISSION_DENIED";
}
