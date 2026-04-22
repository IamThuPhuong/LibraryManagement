package main.vo;

import main.constants.AuthorConstants;
import main.enums.Gender;

import java.time.LocalDate;

public class UserDetailVO {
    private String userName;
    private String password;
    private String fullName = AuthorConstants.INIT_STRING;
    private LocalDate birthDay = AuthorConstants.INIT_DATE;
    private String idCard = AuthorConstants.INIT_STRING;
    private String address = AuthorConstants.INIT_STRING;
    private Gender gender = Gender.OTHER;

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

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
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
}