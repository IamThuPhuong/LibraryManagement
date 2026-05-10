package main.vo;

import main.constants.Constants;
import main.enums.Gender;
import main.enums.UserRole;

import java.time.LocalDate;

public class UserDetailVO {
    private String userName;
    private String password;
    private String fullName = Constants.INIT_STRING;
    private LocalDate birthDay = Constants.INIT_DATE;
    private String idCard = Constants.INIT_STRING;
    private String address = Constants.INIT_STRING;
    private Gender gender = Gender.OTHER;
    private UserRole userRole = UserRole.OFFICER;

    public UserDetailVO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    /** Dùng String để tránh các lỗi parse LocalDate */
    public void setBirthDay(String birthDayStr) {
        // Kiểm tra null hoặc rỗng
        if (birthDayStr == null || birthDayStr.trim().isEmpty()) {
            this.birthDay = LocalDate.parse(
                    Constants.STRING_INIT_DATE,
                    Constants.DATE_FORMATTER
            );
            return;
        }

        try {
            // Parse String thành LocalDate theo format
            this.birthDay = LocalDate.parse(birthDayStr, Constants.DATE_FORMATTER);
        } catch (Exception e) {
            System.out.println("Định dạng ngày sinh không hợp lệ: " + birthDayStr);
            System.out.println("Vui lòng sử dụng định dạng: dd/MM/yyyy");
            // Sử dụng giá trị mặc định nếu parse lỗi
            this.birthDay = LocalDate.parse(
                    Constants.STRING_INIT_DATE,
                    Constants.DATE_FORMATTER
            );
        }
    }
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}