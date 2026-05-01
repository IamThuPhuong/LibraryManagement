package main.repositories;

import main.enums.Gender;
import main.enums.UserRole;
import main.info.user.User;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class UserRepository {
    public Stream<User> stream() {
        Path path = Path.of("src/test/data/user.txt");
        try {
            return Files.lines(path).map(line -> {
                String[] parts = line.split("\\|");
                if (parts.length != 9) {
                    throw new RuntimeException("Invalid user data format: " + line);
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
                return user;
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read user data: " + e.getMessage(), e);
        }

    }
    public List<User> getAllUsers() {
        return this.stream().toList();
    }

    public User findByUserId(String userId) {
        return this.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public User findByUserName(String userName) {
        return this.stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst()
                .orElse(null);
    }
    public List<String> getAllUserNames(){
        return this.stream()
                .map(User::getUserName)
                .toList();
    }

    public void updateUser(User user) throws IOException {
        List<User> users = this.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(user.getUserId())) {
                users.set(i, user);
                break;
            }
        }
        // Ghi đè file data.txt với nội dung mới từ userList sau khi đã cập nhật user
        Path path = Path.of("src/test/data/user.txt");
        StringBuilder sb = new StringBuilder();
        for (User u : users) {
            sb.append(u.getUserId())
                    .append(u.getUserName())
                    .append(u.getPassword())
                    .append(u.getFullName())
                    .append(u.getBirthDay())
                    .append(u.getIdCard())
                    .append(u.getAddress())
                    .append(u.getGender() != null ? u.getGender().name() : "null");
        }
        Files.writeString(path, String.join("|", sb) + "\n", StandardOpenOption.TRUNCATE_EXISTING);
    }
}
