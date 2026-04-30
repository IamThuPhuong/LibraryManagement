package main.repositories;

import main.enums.Gender;
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
                if (parts.length != 8) {
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
                return user;
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read user data: " + e.getMessage(), e);
        }

    }
    public List<User> getAllUsers() {
        return this.stream().toList();
    }

    public User findByUserName(String userName) throws IOException {
        return this.stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst()
                .orElse(null);
    }
    public List<String> getAllUserNames() throws IOException {
        return this.stream()
                .map(User::getUserName)
                .toList();
    }
}
