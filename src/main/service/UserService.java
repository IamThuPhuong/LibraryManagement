package main.service;

import main.constants.AuthorConstants;
import main.enums.Gender;
import main.enums.Permission;
import main.enums.UserRole;
import main.info.user.User;
import main.repositories.UserRepository;
import main.validate.*;
import main.vo.UserDetailVO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class UserService {

    /** Repository để thao tác với dữ liệu người dùng */
    UserRepository userRepository = new UserRepository();

    /** instance variable: người dùng sau khi đăng nhập */
    private User currentUser = userRepository.findByUserId(AuthenService.USER_ID);

    /** Common xử lý phân quyền */
    private static AuthorService authorService = new AuthorService();

    /** Quyền truy cập class InfoUpdateService */
    private static final Permission PERMISSION_OF_FUNCTION = Permission.MANAGE_USER;

    /** Validate check thông tin user hợp lệ */
    Validator<UserDetailVO> userValidator = new UserValidator();

    /** Validate check quyền truy cập chức năng */
    Validator<Permission> authorValidator = new AuthorValidator(currentUser, authorService);

    /** Validate check thông tin user hợp lệ khi tạo mới user */
    Validator<User> userCreateValidator = new UserCreateDataValidator();

    /** Validate check thông tin user hợp lệ khi cập nhật user */
    Validator<User> userUpdateValidator = new UserCreateDataValidator();

    public UserService() throws IOException {
    }

    public UserService(User currentUser) {
        this.currentUser = currentUser;
    }


    public User updateUser(User user, UserDetailVO vo) throws IllegalArgumentException, ExceptionInInitializerError, IOException {
        authorValidator.validate(PERMISSION_OF_FUNCTION);
        userUpdateValidator.validate(user);
        // Check xem sửa mình hay sửa người
        try {
            if (user == null) {
                throw new IllegalArgumentException("User cần cập nhật không tồn tại!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return user; // Trả về user gốc nếu có lỗi
        } catch (ExceptionInInitializerError e) {
            System.out.println(e.getMessage());
            return user; // Trả về user gốc nếu không có quyền
        }
        if(vo.getUserName() != null && !vo.getUserName().isEmpty()){
            user.setUserName(vo.getUserName());
        }
        if (vo.getFullName() != null && !vo.getFullName().isEmpty()){
            user.setFullName(vo.getFullName());
        }
        // password khong duoc nhap
        if (!vo.getBirthDay().equals(AuthorConstants.INIT_DATE)){
            user.setBirthDay(vo.getBirthDay());
        }
        if (vo.getAddress() != null && !vo.getAddress().isEmpty()){
            user.setAddress(vo.getAddress());
        }
        if (!vo.getGender().equals(Gender.OTHER)){
            user.setGender(vo.getGender());
        }
        // status khong duoc sua

        // Ghi đè file data.txt với nội dung mới từ userList sau khi đã cập nhật user
        userRepository.updateUser(user);

        return user;
    }

    public User createUser(UserDetailVO vo) throws ExceptionInInitializerError, IOException {
        authorValidator.validate(PERMISSION_OF_FUNCTION);
        User currentUser = userRepository.findByUserId(AuthenService.USER_ID);
        // Stream
        List<String> userNameList = userRepository.getAllUserNames();

        // validate input
        userValidator.validate(vo);
        if (currentUser != null){
            authorValidator.validate(PERMISSION_OF_FUNCTION);
        }

        // Create new user
        // new UserID: Sử dụng UUID (không cần kiểm tra trùng lặp vì xác suất trùng gần như 0)
        User newUser = new User();
        String userId = UUID.randomUUID().toString(); // Format: 550e8400-e29b-41d4-a716-446655440000
        newUser.setUserId(userId);

        // new Username: không nằm trong List đã có && không chứa ký tự đặc biệt
        if (!userNameList.contains(vo.getUserName())){
            newUser.setUserName(vo.getUserName());
        }

        // new Password: không trống && không chứa ký tự đặc biệt
        if (!vo.getPassword().isEmpty()){
            newUser.setPassword(vo.getPassword());
        }

        // new FullName
        if (!vo.getFullName().isEmpty()){
            newUser.setFullName(vo.getFullName());
        }
        // new BirthDay
        if (!vo.getBirthDay().equals(AuthorConstants.INIT_DATE)){
            newUser.setBirthDay(vo.getBirthDay());
        }
        
        //new IdCard
        if(!vo.getIdCard().equals(AuthorConstants.INIT_STRING)){
            newUser.setIdCard(vo.getIdCard());
        }
        
        // new Address
        if(!vo.getAddress().equals(AuthorConstants.INIT_STRING)){
            newUser.setAddress(vo.getAddress());
        }
        // new Gender
        if(!vo.getGender().equals(Gender.OTHER)){
            newUser.setGender(vo.getGender());
        }

        if (currentUser != null) {
            if (currentUser.getUserRole().equals(UserRole.ADMIN)) {
                newUser.setUserRole(vo.getUserRole());
            } else if (currentUser.getUserRole().equals(UserRole.MANAGER)) {
                if (vo.getUserRole().equals(UserRole.ADMIN)) {
                    System.out.println("Bạn không có quyền tạo người dùng có vai trò ADMIN!");
                    newUser.setUserRole(UserRole.OFFICER);
                } else {
                    newUser.setUserRole(vo.getUserRole());
                }
            }
        } else {
            // Nếu không có người dùng hiện tại (tức là đang tạo user đầu tiên), mặc định là READER
            newUser.setUserRole(UserRole.READER);
        }

        // add vao data.txt sau khi chuyển sang Spring sẽ add vào DB
        saveUserListToFile(newUser);

        return newUser;
    }

    public List<User> showUserList(List<User> userList) throws ExceptionInInitializerError {
        authorValidator.validate(PERMISSION_OF_FUNCTION);
        System.out.println("Danh sách người dùng:");
        for (User user : userList) {
            user.toString();
        }
        return userList;
    }

    public void saveUserListToFile(User user) throws IOException {
        // Thay thế bằng SQL khi chuyển sang Spring
        // Ghi đè file data.txt với nội dung mới từ userList
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/data/user.txt", true))){
            String line = "\n" + user.getUserId() + "|" + user.getUserName() + "|" + user.getPassword() + "|" +
                    user.getFullName() + "|" + user.getBirthDay() + "|" + user.getIdCard() + "|" +
                    user.getAddress() + "|" + user.getGender() + "|" + user.getUserRole();
            writer.write(line);
    } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    public void overwriteUserListToFile(List<User> userList) throws IOException {
        // Thay thế bằng SQL khi chuyển sang Spring
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/data/user.txt"))) {
            for (User user : userList) {
                String line = user.getUserId() + "," + user.getUserName() + "," + user.getPassword() + "," +
                        user.getFullName() + "," + user.getBirthDay() + "," + user.getIdCard() + "," +
                        user.getAddress() + "," + user.getGender();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

}
