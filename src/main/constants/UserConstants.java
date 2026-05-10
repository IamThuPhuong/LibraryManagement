package main.constants;

import java.time.format.DateTimeFormatter;

public class UserConstants {

    /** Ký tự đặc biệt "!","@","#","$","%","&","*","?" */
    public static final String[] SPECIAL_CHAR_LIST = {"!","@","#","$","%","&","*","?"};

    /** Chiều dài tối đa khi nhập user */
    public static final int USER_MAX_LENGTH = 15;

    /** Format ngày tháng năm (yyyy-MM-dd) - dùng cho database */
    public static final DateTimeFormatter DATE_FORMATTER_DB = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
