package main.constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Lớp AuthorConstants chứa các hằng số liên quan đến tác giả và quản lý người dùng trong hệ thống.
 *
 * @author Thu Phương
 * @version 1.0
 * @since 2026-04-30
 */
public class AuthorConstants {

    /** Cờ đăng xuất */
    public static final int LOUGOUT_FLAG = 1;

    /** Cờ khách ghé website */
    public static final String DEFAULT_USER = "0";

    /** Khởi tạo chuỗi */
    public static final String INIT_STRING = " ";

    /** Ký tự đặc biệt "!","@","#","$","%","&","*","?" */
    public static final String[] SPECIAL_CHAR_LIST = {"!","@","#","$","%","&","*","?"};

    /** Ngày khởi tạo */
    public static final LocalDate INIT_DATE = LocalDate.MIN;

    /** Ngày khởi tạo dưới dạng String - phải khớp với DATE_FORMATTER */
    public static final String STRING_INIT_DATE = "01/01/1970";

    /** Chiều dài tối đa khi nhập user */
    public static final int USER_MAX_LENGTH = 15;

    /** Format ngày tháng năm (dd/MM/yyyy) - dùng cho console */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Format ngày tháng năm (yyyy-MM-dd) - dùng cho database */
    public static final DateTimeFormatter DATE_FORMATTER_DB = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}