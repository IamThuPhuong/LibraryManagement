package main.validate;

/**
 * Interface định nghĩa hợp đồng cho các class validator.
 * Các class implement interface này phải cung cấp logic validate cho đối tượng target.
 *
 * @author Thu Phương
 * @version 1.0
 * @since 2026-04-19
 */
public interface Validator {

    /**
     * Validate thông tin của đối tượng target.
     *
     * <p>Phương thức này sẽ kiểm tra các constraints và business rules
     * của đối tượng được truyền vào. Nếu có lỗi, sẽ in ra console
     * hoặc throw exception tùy theo implementation.
     *
     * <p><b>Lưu ý:</b> Hiện tại implementation chỉ in lỗi ra console.
     * Khi chuyển sang Spring, nên cải thiện bằng cách collect lỗi
     * vào list và return, hoặc throw checked exception.
     *
     * @param target đối tượng cần validate (thường là VO hoặc DTO)
     * @throws ClassCastException nếu target không phải kiểu mong đợi
     * @throws NullPointerException nếu target là null và implementation không handle
     * @see InfoValidator#validate(Object)
     */
    public void validate(Object target);
}