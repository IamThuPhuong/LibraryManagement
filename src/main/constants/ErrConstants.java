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
    public static final String PASSWORD_NEEDTO_USE_SPECIAL_KEY = "PASSWORD_NEEDTO_USE_SPECIAL_KEY";

    /** Password cần chứa ít nhất 1 chữ số */
    public static final String PASSWORD_NEEDTO_USE_NUMBER = "PASSWORD_NEEDTO_USE_NUMBER";

    /** Password cần chứa ít nhất 1 chữ cái viết hoa */
    public static final String PASSWORD_NEEDTO_USE_UPPERCASE = "PASSWORD_NEEDTO_USE_UPPERCASE";

    /** Password cần chứa ít nhất 1 chữ cái viết thường */
    public  static final String PASSWORD_NEEDTO_USE_LOWERCASE = "PASSWORD_NEEDTO_USE_LOWERCASE";

    /** Password không được chứa khoảng trắng */
    public static final String PASSWORD_CANT_USE_SPACE = "PASSWORD_CANT_USE_SPACE";

    /** Password phải có độ dài tối thiểu 8 ký tự để đảm bảo độ mạnh. */
    public static final String PASSWORD_MUSTBE_EXACTLY_LENGTH = "PASSWORD_MUSTBE_EXACTLY_LENGTH";

    /** Password mới không được trùng với password cũ. */
    public static final String NEW_PASSWORD_SAME_AS_OLD = "NEW_PASSWORD_SAME_AS_OLD";
    // ==================== BIRTHDAY ERRORS ====================

    /** Ngày sinh không được là ngày trong tương lai để đảm bảo tính hợp lý của dữ liệu. */
    public static final String BIRTHDAY_AFTER_TODAY = "BIRTHDAY_AFTER_TODAY";

    // ==================== IDCARDNO ERRORS ====================
    /** Căn cước công dân không được để trống */
    public static final String IDCARDNO_CAN_NOT_NULL = "IDCARDNO_CAN_NOT_NULL";

    /** Căn cước công dân đã tồn tại */
    public static final String IDCARDNO_EXISTED = "IDCARDNO_EXISTED";

    /** Căn cước công dân không được chứa ký tự đặc biệt */
    public static final String IDCARDNO_CONTAINS_SPECIAL_KEYS = "IDCARDNO_CONTAINS_SPECIAL_KEYS";

    /** Căn cước công dân chỉ chứa số */
    public static final String IDCARDNO_CONTAINS_LETTERS = "IDCARDNO_CONTAINS_LETTERS";

    /** Độ dài căn cước không hợp lệ */
    public static final String IDCARD_LENGTH_INVALID = "IDCARD_LENGTH_INVALID";

    // ==================== FULL NAME ERRORS ====================
    /** Họ tên không được để trống */
    public static final String FULLNAME_CAN_NOT_NULL = "FULLNAME_CAN_NOT_NULL";

    // ==================== EMAIL ERRORS ====================
    /** Email không hợp lệ */
    public static final String INVALID_MAIL = "INVALID_MAIL";

    // ==================== BOOK ERRORS ====================
    /** Mã ISBN không được để trống */
    public static final String ISBN_CAN_NOT_NULL = "ISBN_CAN_NOT_NULL";

    /** Tên sách không được để trống */
    public static final String NAME_CAN_NOT_NULL = "NAME_CAN_NOT_NULL";

    /** Tác giả không được để trống */
    public static final String AUTHOR_CAN_NOT_NULL = "AUTHOR_CAN_NOT_NULL";

    /** Nhà xuất bản không được để trống */
    public static final String PUBLISHER_CAN_NOT_NULL = "PUBSLIHER_CAN_NOT_NULL";

    /** Năm xuất bản không được để trống */
    public static final String PUBLISHYEAR_CAN_NOT_NULL = "PUBLISHYEAR_CAN_NOT_NULL";

    /** Giá cả không hợp lệ */
    public static final String PRICE_INVALID = "PRICE_INVALID";

    /** Số lượng không hợp lệ */
    public static final String TOTAL_INVALID = "TOTAL_INVALID";

    /** Số năm không được lớn hơn năm hiện tại */
    public static final String PUBLISHYEAR_IN_FUTURE = "PUBLISHYEAR_IN_FUTURE";
}
