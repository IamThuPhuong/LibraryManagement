package main.service;

import main.constants.AuthorConstants;
import main.enums.Gender;
import main.enums.Permission;
import main.info.user.User;
import main.validate.AuthorValidator;
import main.validate.InfoValidator;
import main.validate.Validator;
import main.vo.UserDetailVO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InfoUpdateService {

    /** instance variable: người dùng sau khi đăng nhập */
    private User currentUser;

    /** instance variable: danh sách người dùng */
    private List<User> userList;

    /** Common xử lý phân quyền */
    private static AuthorService authorService = new AuthorService();

    /** Quyền truy cập class InfoUpdateService */
    private static final Permission  PERMISSION = Permission.MANAGE_USER;

    /** Validate check thông tin user hợp lệ */
    Validator validator = new InfoValidator(userList);

    /** Validate check quyền truy cập chức năng */
    Validator authorValidator = new AuthorValidator(currentUser, authorService);

    public InfoUpdateService() {
    }

    public InfoUpdateService(User currentUser, List<User> userList) {
        this.currentUser = currentUser;
        this.userList = userList;
    }

    public User updateUser(User user, UserDetailVO vo){
        // Check xem sửa mình hay sửa người
        if(!currentUser.equals(user)){
            authorValidator.validate(PERMISSION);
        }
        if(!vo.getUserName().isEmpty()){
            user.setUserName(vo.getUserName());
        }
        if (!vo.getFullName().isEmpty()){
            user.setFullName(vo.getFullName());
        }
        // password khong duoc nhap
        if (!vo.getBirthDay().equals(user.getBirthDay())){
            user.setBirthDay(vo.getBirthDay());
        }
        if (!vo.getAddress().isEmpty()){
            user.setAddress(vo.getAddress());
        }
        if (!vo.getGender().equals(user.getGender())){
            user.setGender(vo.getGender());
        }
        // status khong duoc sua
        return user;
    }

    public List<User> createUser(List<User> userList, UserDetailVO vo) throws ExceptionInInitializerError{
        // Thay the SQL
        List<String> userNameList = new ArrayList<>();
        for (User user : userList){
            userNameList.add(user.getUserName());
        }

        // validate input
        validator.validate(vo);
        if (currentUser != null){
            authorValidator.validate(PERMISSION);
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

        userList.add(newUser);

        return userList;
    }

    public List<User> showUserList(List<User> userList){
        authorValidator.validate(PERMISSION);
        System.out.println("Danh sách người dùng:");
        for (User user : userList) {
            user.toString();
        }
        return userList;
    }

}
