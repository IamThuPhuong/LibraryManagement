package main.vo;

import main.constants.Constants;
import main.enums.Gender;

import java.time.LocalDate;

public class ReaderDetailVO {
    private String fullName = Constants.INIT_STRING;
    private String idCard = Constants.INIT_STRING;
    private LocalDate birthDate = Constants.INIT_DATE;
    private Gender gender = Gender.OTHER;
    private String email = Constants.INIT_STRING;
    private String address = Constants.INIT_STRING;
    private LocalDate startDate = Constants.TODAY;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDay(String birthDayStr) {
        // Kiểm tra null hoặc rỗng
        if (birthDayStr == null || birthDayStr.trim().isEmpty()) {
            this.birthDate = LocalDate.parse(
                    Constants.STRING_INIT_DATE,
                    Constants.DATE_FORMATTER
            );
            return;
        }

        try {
            // Parse String thành LocalDate theo format
            this.birthDate = LocalDate.parse(birthDayStr, Constants.DATE_FORMATTER);
        } catch (Exception e) {
            System.out.println("Định dạng ngày sinh không hợp lệ: " + birthDayStr);
            System.out.println("Vui lòng sử dụng định dạng: dd/MM/yyyy");
            // Sử dụng giá trị mặc định nếu parse lỗi
            this.birthDate = LocalDate.parse(
                    Constants.STRING_INIT_DATE,
                    Constants.DATE_FORMATTER
            );
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        // Kiểm tra null hoặc rỗng
        if (startDate == null || startDate.trim().isEmpty()) {
            this.startDate = LocalDate.parse(
                    Constants.STRING_INIT_DATE,
                    Constants.DATE_FORMATTER
            );
            return;
        }

        try {
            // Parse String thành LocalDate theo format
            this.startDate = LocalDate.parse(startDate, Constants.DATE_FORMATTER);
        } catch (Exception e) {
            System.out.println("Định dạng ngày sinh không hợp lệ: " + startDate);
            System.out.println("Vui lòng sử dụng định dạng: dd/MM/yyyy");
            // Sử dụng giá trị mặc định nếu parse lỗi
            this.startDate = LocalDate.parse(
                    Constants.STRING_INIT_DATE,
                    Constants.DATE_FORMATTER
            );
        }
    }
}
