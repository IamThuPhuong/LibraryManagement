package test.service;

import main.enums.Gender;
import main.info.user.User;
import main.repositories.UserRepository;
import main.service.AuthenService;
import main.service.UserService;
import main.vo.UserDetailVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorServiceTest {
    private final UserRepository userRepository = new UserRepository();
    public static String tokenClientSide = "";
    public static Scanner input = new Scanner(System.in);
    public static List<User> USERLIST = new ArrayList<>();
    public static User currentUser = new User();

    public static void main(String[] args) throws IOException {

        System.out.println("Chọn 1 chức năng:");
        System.out.println("1. Đăng nhập");
        System.out.println("2. Đăng ký");

        String chosenService = input.nextLine();

        AuthorServiceTest authorServiceTest = new AuthorServiceTest();
        switch (chosenService) {
            case "1":
                authorServiceTest.loginLibrary();
                break;
            case "2":
                authorServiceTest.createUser();
                break;
        }

        if (currentUser != null) {
            System.out.println("Chọn chức năng");
            System.out.println("Quản lý người dùng (admin only - hiện tạm để test phân quyền)");
            System.out.println("1. Tạo người dùng");
            System.out.println("2. Cập nhật thông tin người dùng");
            System.out.println("Quản lý thông tin cá nhân");
            System.out.println("3. Cập nhật thông tin cá nhân");
            System.out.println("9. Đăng xuất");

            chosenService = input.nextLine();

            AuthenService authenService = new AuthenService();
            UserService userService = new UserService(currentUser);
            AuthorServiceTest authorServiceTest1 = new AuthorServiceTest();
            UserRepository  userRepository = new UserRepository();
            switch (chosenService) {
                case "1":
                    authorServiceTest1.createUser();
                case "2":
                    userRepository.getAllUsers().forEach(user -> System.out.println("Username: " + user.getUserName() + " - FullName: " + user.getFullName()));
                    System.out.println("Nhập username của người dùng muốn cập nhật:");
                    String username = input.nextLine();
                    User userToUpdate = authenService.findUser(username);
                    authorServiceTest1.updateUser(userToUpdate);
                case "3":
                    authorServiceTest1.updateUser(currentUser);
                case "9":
                    boolean checkLogout = authenService.logoutService(currentUser);
                    if (checkLogout) {
                        currentUser = null; // TODO - sau này sẽ xóa token client side, hoặc xóa session server side
                        System.out.println("Đăng xuất thành công!");
                    } else {
                        System.out.println("Đăng xuất thất bại!");
                    }
            }
        }

    }

    public User loginLibrary() {
        System.out.println("Đăng nhập vào thư viện:");
        System.out.println("Username:");
        String username = input.nextLine();
        System.out.println("Password");
        String password = input.nextLine();

        AuthenService authenService = new AuthenService();
        User user = authenService.loginService(username, password);
        currentUser = user;
        return user;
    }

    /**
     * Tạo user phía màn hình
     */
    public void createUser() throws IOException {
        UserService userService = new UserService();
        AuthenService authenService = new AuthenService();
        // Thay thế màn hình=> form nhập thông tin người dùng
        UserDetailVO userDetailVO = new UserDetailVO();
        System.out.println("Form nhập thông tin người dùng:");
        System.out.print("1. Username (*):");
        String username = input.nextLine();
        while (username.isEmpty() || userRepository.findByUserName(username) != null) {
            System.out.println("Username không hợp lệ hoặc đã tồn tại! Vui lòng nhập lại:");
            username = input.nextLine();
        }
        System.out.print("\n2. Password (*):");
        String password = input.nextLine();
        while (password.isEmpty()) {
            System.out.println("Password không được để trống! Vui lòng nhập lại:");
            password = input.nextLine();
        }
        System.out.print("\n3. Fullname:");
        String fullName = input.nextLine();
        System.out.print("\n4. BirthDay (yyyy-MM-dd):");
        String birthDay = input.nextLine();
        System.out.print("\n5. Id Card:");
        String idCard = input.nextLine();
        System.out.print("\n6. Address:");
        String address = input.nextLine();
        System.out.print("\n7. Gender (Chose: 1.Male /2.Female):");
        String gender = input.nextLine();
        Gender genderEnum;
        switch (gender) {
            case "1":
                genderEnum = Gender.MALE;
                break;
            case "2":
                genderEnum = Gender.FEMALE;
                break;
            default:
                genderEnum = Gender.OTHER;
        }

        // Set thông tin người dùng từ form vào UserDetailVO
        userDetailVO.setUserName(username);
        userDetailVO.setPassword(password);
        userDetailVO.setFullName(fullName);
        userDetailVO.setBirthDay(birthDay);
        userDetailVO.setIdCard(idCard);
        userDetailVO.setAddress(address);
        userDetailVO.setGender(genderEnum);


        userService.createUser(USERLIST, userDetailVO);
        System.out.println("Tạo người dùng thành công! Thông tin người dùng mới:");
        System.out.println("Username: " + username);

        System.out.println("Bạn có muốn đăng nhập ngay không? (Y/N)");
        String loginChoice = input.nextLine();
        if (loginChoice.equalsIgnoreCase("Y")) {
            loginLibrary();
        } else {
            System.out.println("Bạn có thể đăng nhập sau từ menu chính.");
        }

    }

    /**
     * Cập nhật user phía màn hình
     */
    public void updateUser(User user) throws IOException {

        //====[START] MAN HINH
        UserDetailVO vo = new UserDetailVO();
        String chosenInfo = "";
        do {
            System.out.println("=========THÔNG TIN NGƯỜI DÙNG===========");
            System.out.println("1. Username: " + user.getUserName());
            System.out.println("2. Password: " + user.getPassword());
            System.out.println("3. FullName: " + user.getFullName());
            System.out.println("4. BirthDay: " + user.getBirthDay());
            System.out.println("5. IdCard: " + user.getIdCard());
            System.out.println("6. Address: " + user.getAddress());
            System.out.println("7. Gender: " + user.getGender());
            System.out.println("Chọn thông tin muốn cập nhật (1-7), hoặc 0 để thoát:");

            chosenInfo = input.nextLine();


            switch (chosenInfo) {
                case "1":
                    System.out.println("Nhập username mới:");
                    String newUsername = input.nextLine();
                    vo.setUserName(newUsername);
                    break;
                case "2":
                    System.out.println("Nhập password mới:");
                    String newPassword = input.nextLine();
                    vo.setPassword(newPassword);
                    break;
                case "3":
                    System.out.println("Nhập fullname mới:");
                    String newFullName = input.nextLine();
                    vo.setFullName(newFullName);
                    break;
                case "4":
                    System.out.println("Nhập birthday mới (dd-MM-yyyy):");
                    String newBirthDay = input.nextLine();
                    vo.setBirthDay(newBirthDay);
                    break;
                case "5":
                    System.out.println("Nhập id card mới:");
                    String newIdCard = input.nextLine();
                    vo.setIdCard(newIdCard);
                    break;
                case "6":
                    System.out.println("Nhập address mới:");
                    String newAddress = input.nextLine();
                    vo.setAddress(newAddress);
                    break;
                case "7":
                    System.out.println("Chọn giới tính mới (1.Male /2.Female):");
                    String newGender = input.nextLine();
                    switch (newGender) {
                        case "1":
                            user.setGender(Gender.MALE);
                            break;
                        case "2":
                            user.setGender(Gender.FEMALE);
                            break;
                        default:
                            user.setGender(Gender.OTHER);
                    }
                    vo.setGender(user.getGender());
                    break;
            }

        } while (!chosenInfo.equals("0"));

        // =[END]=MAN HINH

        UserService userService = new UserService();
        userService.updateUser(user, vo);

        System.out.println("Cập nhật thông tin thành công! Thông tin mới:");
        System.out.println("Username: " + user.getUserName());
        System.out.println("Password: " + user.getPassword());
        System.out.println("FullName: " + user.getFullName());
        System.out.println("BirthDay: " + user.getBirthDay());
        System.out.println("IdCard: " + user.getIdCard());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Gender: " + user.getGender());

    }


}