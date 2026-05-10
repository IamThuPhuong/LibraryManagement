package main.constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Constants {
    /** Khởi tạo chuỗi */
    public static final String INIT_STRING = " ";

    /** Ngày khởi tạo */
    public static final LocalDate INIT_DATE = LocalDate.MIN;

    /** Ngày khởi tạo dưới dạng String - phải khớp với DATE_FORMATTER */
    public static final String STRING_INIT_DATE = "01/01/1970";

    /** Format ngày tháng năm (dd/MM/yyyy) - dùng cho console */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
}
