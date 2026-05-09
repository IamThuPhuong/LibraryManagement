package main.constants;

/**
 * Lớp AuthorConstants chứa các hằng số liên quan đến tác giả và quản lý người dùng trong hệ thống.
 *
 * @author Thu Phương
 * @version 1.0
 * @since 2026-04-30
 */
public class AuthorConstants {
    // ==================== PERMISSION ERRORS ====================
    /** Permission phải được chỉ định rõ ràng để kiểm tra quyền hạn. */
    public static final String PERMISSION_NULL = "Bạn không có quyền thực hiện hành động này!";
    /** Hệ thống từ chối truy cập do thiếu quyền hạn phù hợp. */
    public static final String PERMISSION_DENIED = "Quyền hạn không được để trống!";
}