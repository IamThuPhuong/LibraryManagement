package main.entity;

import main.enums.Gender;
import main.enums.Status;
import main.enums.UserRole;

import java.time.LocalDate;

public class User {
    private String userId;
    private String userName;
    private String password;
    private String fullName;
    private LocalDate birthDay;
    private String idCard;
    private String address;
    private Gender gender;
    private Status status;
    private UserRole userRole;

    public User() {
    }

    public User(String userId, String userName, String password, String fullName, LocalDate birthDay, String idCard, String address, Gender gender, Status status, UserRole role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.idCard = idCard;
        this.address = address;
        this.gender = gender;
        this.status = status;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password.hashCode() + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthDay=" + birthDay +
                ", idCard='" + idCard + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", status=" + status +
                ", userRole=" + userRole +
                '}';
    }
}
