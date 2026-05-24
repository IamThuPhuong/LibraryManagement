package test;

import main.entity.User;
import main.repository.UserRepository;
import main.service.AuthenService;
import test.service.*;

import java.io.IOException;
import java.util.Scanner;

public class MainMenuTest {
    public static Scanner input = new Scanner(System.in);
    public static User currentUser = new User();
    public static UserRepository userRepository = new UserRepository();

    public static void main(String[] args) throws IOException {
//        System.out.println("Chọn 1 chức năng:");
//        System.out.println("1. Đăng nhập");
        //System.out.println("2. Đăng ký");

//        String chosenService = input.nextLine();
        while (true) {
            try {
                showMainMenu();
            } catch (IllegalArgumentException e) {
                System.out.println("Lỗi: " + e.getMessage());
            } catch (ExceptionInInitializerError e) {
                System.out.println("Lỗi khởi tạo: " + e.getMessage());
            }
        }
    }

    public static void showMainMenu() throws IOException {
        String chosenService;

        // Test chức năng 1
        AuthenServiceTest authenServiceTest = new AuthenServiceTest();
        UserServiceTest userServiceTest = new UserServiceTest();

        AuthenService authenService = new AuthenService();
        // session login
        boolean isLogin = authenService.checkLogin();

        while (!isLogin) {
            isLogin = authenServiceTest.loginLibrary();
        }

        // After login
        currentUser = userRepository.findByUserId(AuthenService.USER_ID);
        // Test chức năng 2
        ReaderCardServiceTest readerCardServiceTest = new ReaderCardServiceTest();
        BookServiceTest bookServiceTest = new BookServiceTest();
        System.out.println("Chọn chức năng");
        System.out.println("Quản lý người dùng");
        System.out.println("1. Tạo người dùng");
        System.out.println("2. Cập nhật thông tin người dùng");
        System.out.println("Quản lý thông tin cá nhân");
        System.out.println("3. Cập nhật thông tin cá nhân");
        System.out.println("4. Đổi mật khẩu");
        System.out.println("Quản lý độc giả");
        System.out.println("5. Xem danh sách độc giả");
        System.out.println("6. Thêm độc giả");
        System.out.println("7. Cập nhật thông tin độc giả");
        System.out.println("8. Tìm kiếm độc giả");
        System.out.println("Quản lý sách:");
        System.out.println("9. Xem danh sách sách hiện có");
        System.out.println("10. Thêm sách");
        System.out.println("11. Cập nhật thông tin sách");
        System.out.println("12. Tìm kiếm sách");
        System.out.println("Quản lý mượn trả: ");
        System.out.println("13. Lập phiếu mượn sách");
        System.out.println("14. Lập phiếu trả sách");
        System.out.println("Thống kê:");
        System.out.println("15. Thống kê cơ bản");
        System.out.println("99. Đăng xuất");

        chosenService = input.nextLine();

        UserRepository userRepository = new UserRepository();

        switch (chosenService) {
            case "1":
                userServiceTest.createUser();
                break;
            case "2":
                userRepository.getAll().forEach(user -> System.out.println("Username: " + user.getUserName() + " - FullName: " + user.getFullName()));
                System.out.println("Nhập username của người dùng muốn cập nhật:");
                String username = input.nextLine();
                User userToUpdate = authenService.findUser(username);
                userServiceTest.updateUser(userToUpdate);
                break;
            case "3":
                userServiceTest.updateUser(currentUser);
                break;
            case "4":
                authenServiceTest.changePassword(currentUser);
                break;
            case "5":
                readerCardServiceTest.showReaderList();
                break;
            case "6":
                readerCardServiceTest.addReader();
                break;
            case "7":
                readerCardServiceTest.updateReader();
                break;
            case "8":
                System.out.println("Chọn nội dung tìm kiếm:");
                System.out.println("1. Tìm kiếm theo họ tên");
                System.out.println("2. Tìm kiếm theo CMND");
                String chosen = input.nextLine();
                if(chosen.equals("1")){
                    input.nextLine();
                    readerCardServiceTest.findReaderByFullName();
                } else {
                    input.nextLine();
                    readerCardServiceTest.findReaderByIdCardNo();
                }
                break;
            case "9":
                bookServiceTest.showBookList();
                break;
            case "10":
                bookServiceTest.addBook();
                break;
            case "11":
                bookServiceTest.updateBook();
                break;
            case "12":
                System.out.println("Chọn nội dung tìm kiếm:");
                System.out.println("1. Tìm kiếm theo ISBN");
                System.out.println("2. Tìm kiếm theo tên");
                String chosenFindBook = input.nextLine();
                if(chosenFindBook.equals("1")){
                    bookServiceTest.findBookByIsbn();
                } else {
                    bookServiceTest.findBookByName();
                }
                break;
            case "13":
                BorrowCardServiceTest borrowCardServiceTest = new BorrowCardServiceTest();
                borrowCardServiceTest.testCreateBorrowCard();
                break;
            case "14":
                BorrowCardServiceTest borrowCardServiceTest1 = new BorrowCardServiceTest();
                borrowCardServiceTest1.testCreateReturnedCard();
                break;
            case "15":
                DashboardServiceTest dashboardServiceTest = new DashboardServiceTest();
                dashboardServiceTest.testShowDashboard();
                break;
            case "99":
                boolean checkLogout = authenService.logout();
                if (checkLogout) {
                    currentUser = null;
                    System.out.println("Đăng xuất thành công!");
                } else {
                    System.out.println("Đăng xuất thất bại!");
                }
                break;
        }

    }


}
