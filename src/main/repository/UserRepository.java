package main.repository;

import main.enums.Gender;
import main.enums.Status;
import main.enums.UserRole;
import main.entity.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class UserRepository {
    public Stream<User> stream() {
        Path path = Path.of("src/test/data/user.csv");
        try {
            return Files.lines(path).map(line -> {
                String[] parts = line.split("\\|");
                try{
                    if (parts.length != 10) {
                        throw new RuntimeException("Invalid user data format:" + line);
                    }
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                    return null; // Trả về null nếu dữ liệu không hợp lệ
                }
                User user = new User();
                user.setUserId(parts[0]);
                user.setUserName(parts[1]);
                user.setPassword(parts[2]);
                user.setFullName(parts[3]);
                user.setBirthDay(LocalDate.parse(parts[4]));
                user.setIdCard(parts[5]);
                user.setAddress(parts[6]);
                if (parts[7] != null && !parts[7].isEmpty() && !parts[7].equals("null")) {
                    user.setGender(Gender.valueOf(parts[7]));
                }
                if (parts[8] != null && !parts[8].isEmpty() && !parts[8].equals("null")) {
                    user.setUserRole(UserRole.valueOf(parts[8]));
                }
                if (parts[9] != null && !parts[9].isEmpty() && !parts[9].equals("null")) {
                    user.setStatus(Status.valueOf(parts[9]));
                }
                return user;
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read user data: " + e.getMessage(), e);
        }

    }
    public List<User> getAllUsers() {
        return this.stream().toList();
    }

    public User findByUserId(String userId) throws NullPointerException {
        if (userId == null || userId.isEmpty()) {
            throw new NullPointerException("User ID không được để trống!");
        }
        return this.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public User findByUserName(String userName) throws NullPointerException {
        if (userName == null || userName.isEmpty()) {
            throw new NullPointerException("User name không được để trống!");
        }
        return this.stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst()
                .orElse(null);
    }
    public List<User> findByUserRole(UserRole userRole){
        return this.stream()
                .filter(user -> user.getUserRole().equals(userRole))
                .toList();
    }

    public List<String> getAllUserNames(){
        return this.stream()
                .map(User::getUserName)
                .toList();
    }

    public void saveUserListToFile(User user)  {
        // Thay thế bằng SQL khi chuyển sang Spring
        // Ghi đè file data.csv với nội dung mới từ userList
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/data/user.txt", true))){
            String line = user.getUserId() + "|" + user.getUserName() + "|" + user.getPassword() + "|" +
                    user.getFullName() + "|" + user.getBirthDay() + "|" + user.getIdCard() + "|" +
                    user.getAddress() + "|" + user.getGender() + "|" + user.getUserRole() + "|" + user.getStatus();
            writer.newLine();
            writer.write(line);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    public void updateUser(User user) throws IOException {
        try {
            if (user == null) {
                throw new IllegalArgumentException("Check lại file data.csv!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        List<User> users = new ArrayList<>(this.getAllUsers());
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(user.getUserId())) {
                users.set(i, user);
                break;
            }
        }
        // Ghi đè file data.txt với nội dung mới từ userList sau khi đã cập nhật user
        Path path = Path.of("src/test/data/user.csv");
        StringBuilder sb = new StringBuilder();
        for (User u : users) {
            sb.append(u.getUserId()).append("|")
                    .append(u.getUserName()).append("|")
                    .append(u.getPassword()).append("|")
                    .append(u.getFullName()).append("|")
                    .append(u.getBirthDay()).append("|")
                    .append(u.getIdCard()).append("|")
                    .append(u.getAddress()).append("|")
                    .append(u.getGender() != null ? u.getGender().name() : "null").append("|")
                    .append(u.getUserRole() != null ? u.getUserRole().name() : "null").append("|")
                    .append(u.getStatus() != null ? u.getStatus().name() : "null")
                    .append("\n");
        }

        Files.writeString(path, sb.toString(), StandardOpenOption.TRUNCATE_EXISTING);
    }
}
