package test.service;

import main.enums.Gender;
import main.enums.UserRole;
import main.entity.User;
import main.repository.UserRepository;
import main.service.AuthenService;
import main.service.UserService;
import main.vo.UserVO;

import java.io.IOException;

import static test.service.AuthenServiceTest.input;

public class UserServiceTest {
    private final UserRepository userRepository = new UserRepository();

    /**
     * Tạo user phía màn hình
     */
    public void createUser() {
        User currentUser = userRepository.findByUserId(AuthenService.USER_ID);
        // Validate phía màn hình
//        if (currentUser.getUserRole() != UserRole.ADMIN && currentUser.getUserRole() != UserRole.MANAGER) {
//            System.out.println("Bạn không có quyền tạo người dùng mới!");
//            return;
//        }
        UserService userService = new UserService();
        // Thay thế màn hình=> form nhập thông tin người dùng
        UserVO userVO = new UserVO();
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
        System.out.print("\n4. BirthDay (dd/MM/yyyy):");
        String birthDay = input.nextLine();
        System.out.print("\n5. Id Card:");
        String idCard = input.nextLine();
        System.out.print("\n6. Address:");
        String address = input.nextLine();
        System.out.print("\n7. Gender (Chose: 1.Male /2.Female):");
        String gender = input.nextLine();
        Gender genderEnum = switch (gender) { // Sử dụng switch expression của Java 14+ để gán giá trị enum
            case "1" -> Gender.MALE;
            case "2" -> Gender.FEMALE;
            default -> Gender.OTHER;
        };


        if (currentUser != null) {
            System.out.println("Bạn đang đăng nhập với quyền: " + currentUser.getUserRole());
            if (currentUser.getUserRole() == UserRole.ADMIN) {
                System.out.println("Chọn quyền:");
                System.out.println("\n8. User Role (Chose: 1.Manager /2.Officer):");
                switch (input.nextLine()) {
                    case "1":
                        userVO.setUserRole(UserRole.MANAGER);
                        break;
                    case "2":
                        userVO.setUserRole(UserRole.OFFICER);
                        break;
                }
            } else //if (currentUser.getUserRole() == UserRole.MANAGER) {
            {
                userVO.setUserRole(UserRole.OFFICER);
//            } else {
//                System.out.println("Bạn không có quyền tạo người dùng mới!");
//                return;
            }
        } else {
            System.out.println("Bạn đang đăng ký tài khoản mới!");
        }

        // Set thông tin người dùng từ form vào UserDetailVO
        userVO.setUserName(username);
        userVO.setPassword(password);
        userVO.setFullName(fullName);
        userVO.setBirthDay(birthDay);
        userVO.setIdCard(idCard);
        userVO.setAddress(address);
        userVO.setGender(genderEnum);

        // Service
        User newUser = userService.createUser(userVO);
        System.out.println("Kết thúc chức năng tạo người dùng");

        if(newUser != null) {
            System.out.println("Bạn có muốn đăng nhập ngay không? (Y/N)");
            String loginChoice = input.nextLine();
            if (loginChoice.equalsIgnoreCase("Y")) {
                AuthenServiceTest authenServiceTest = new AuthenServiceTest();
                // Service
                authenServiceTest.loginLibrary();
            } else {
                System.out.println("Bạn có thể đăng nhập sau từ menu chính.");
            }
        }

    }

    /**
     * Cập nhật user phía màn hình
     */
    public void updateUser(User user) throws IOException {

        //====[START] MAN HINH
        UserVO vo = new UserVO();
        String chosenInfo;
        do {
            System.out.println("=========THÔNG TIN NGƯỜI DÙNG===========");
            System.out.println("1. Username: " + user.getUserName());
            System.out.println("2. Password: " + user.getPassword());
            System.out.println("3. FullName: " + user.getFullName());
            System.out.println("4. BirthDay: " + user.getBirthDay());
            System.out.println("5. IdCard: " + user.getIdCard());
            System.out.println("6. Address: " + user.getAddress());
            System.out.println("7. Gender: " + user.getGender());
            System.out.println("8. Status: " + user.getStatus());
            System.out.println("========================================");
            System.out.println("Chọn thông tin muốn cập nhật (1-7), hoặc 0 để thoát:");

            chosenInfo = input.nextLine();


            switch (chosenInfo) {
                case "1":
                    System.out.println("Username không được thay đổi!");
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
                    System.out.println("Nhập birthday mới (dd/MM/yyyy):");
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
        System.out.println("Status: " + user.getStatus());

    }
}
