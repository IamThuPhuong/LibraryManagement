package main.validate;

import main.vo.UserChangePasswordVO;

import java.util.List;

/**
 * Interface định nghĩa hợp đồng cho các class validator.
 * Các class implement interface này phải cung cấp logic validate cho đối tượng target.
 *
 * @author Thu Phương
 * @version 1.0
 * @since 2026-04-19
 */
public interface Validator<T> {

    /**
     * Validate thông tin của đối tượng target.
     * @param target đối tượng cần validate (thường là VO hoặc DTO)
     */
    public List<String> validate(T target);
}