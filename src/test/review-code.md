# Review cấu trúc lớp service

Ngày review: 2026-05-31

## Phạm vi đã review

- `src/main/service/AuthenService.java`
- `src/main/service/AuthorService.java`
- `src/main/service/BookService.java`
- `src/main/service/BorrowService.java`
- `src/main/service/DashboardService.java`
- `src/main/service/ReaderCardService.java`
- `src/main/service/UserService.java`
- Tham chiếu thêm `src/main/validate/AuthorValidator.java` và một phần repository/VO liên quan.

## Các vấn đề chính

### 1. Service phụ thuộc ngược vào package test

Nhiều service đang import trực tiếp:

```java
import static test.MainMenuTest.userRepository;
```

Xuất hiện ở:

- `BookService`
- `ReaderCardService`
- `BorrowService`
- `DashboardService`
- `AuthenService`

Đây là vấn đề kiến trúc vì code trong `main` không nên phụ thuộc vào code trong `test` hoặc menu test. Nếu sau này đổi UI/menu hoặc chạy production thật, các service này vẫn bị buộc phụ thuộc `MainMenuTest`.

Đề xuất:

- Inject `UserRepository` vào service qua constructor.
- Hoặc tạo một lớp quản lý session như `AuthContext` / `SessionService` để service lấy user hiện tại.

### 2. `currentUser` bị cache sai thời điểm

Các service như `BookService`, `ReaderCardService`, `BorrowService`, `DashboardService`, `UserService` đang lấy `currentUser` ngay khi khởi tạo object:

```java
private User currentUser = userRepository.findByUserId(AuthenService.USER_ID);
Validator<Permission> authorValidator = new AuthorValidator(currentUser, authorService);
```

Nếu service được tạo trước khi login, `currentUser` có thể là null hoặc user cũ. Sau khi login/logout, `authorValidator` trong các service khác không tự cập nhật lại.

Đề xuất:

- Không lưu `currentUser` cố định trong field của service.
- Mỗi lần gọi method cần quyền thì lấy user hiện tại từ session/repository.
- Hoặc truyền `currentUser` vào method/service khi xử lý request.

### 3. `AuthenService.USER_ID` là global static state

`AuthenService.USER_ID` đang là biến static dùng chung toàn chương trình:

```java
public static String USER_ID = AuthenConstants.IS_NOT_LOGIN_FLAG;
```

`checkLogin()` hiện chỉ kiểm tra khác null:

```java
return AuthenService.USER_ID != null;
```

Nếu `IS_NOT_LOGIN_FLAG` khác null thì logout xong vẫn có thể được xem là đang login.

Đề xuất:

- Check rõ:

```java
return !AuthenConstants.IS_NOT_LOGIN_FLAG.equals(USER_ID);
```

- Khi logout nên reset cả `currentUser`.
- Về lâu dài nên thay static state bằng `SessionService`.

### 4. Service đang trộn business logic với nhập/xuất console

Ví dụ:

- `AuthenService.logout()` dùng `Scanner` để hỏi người dùng.
- Nhiều service in lỗi bằng `System.out.println`.
- `AuthorService.getPermissionsByRole()` in thông tin quyền mỗi lần check.

Service nên tập trung xử lý nghiệp vụ, không nên phụ trách UI.

Đề xuất:

- Menu/controller chịu trách nhiệm đọc input và in output.
- Service trả boolean/object/result hoặc throw exception có message rõ ràng.
- Không gọi `Scanner` trong service.

### 5. Kiểm tra quyền chưa nhất quán

Một số method có validate quyền, một số method bỏ sót:

- `BookService.create()` và `BookService.update()` có check `MANAGE_BOOK`.
- `BookService.deleteBook()` chưa check quyền.
- `ReaderCardService.delete()` có check `DELETE_READER`.
- `ReaderCardService.findReaderByIdCardNo()` và `findReaderByFullName()` chưa check quyền.
- `BookService.findByISBN()` và `findByName()` có check `COMMON`.

Đề xuất:

- Mỗi public method trong service nên xác định rõ permission cần dùng.
- Chuẩn hóa bằng helper private, ví dụ:

```java
private void requirePermission(Permission permission) {
    User currentUser = sessionService.getCurrentUser();
    authorService.checkPermission(currentUser, permission);
}
```

### 6. Null check và thứ tự xử lý trong `UserService.updateUser()`

Trong `UserService.updateUser()`, code có thể dùng `user` trước khi kiểm tra null:

```java
if(!vo.getUserRole().equals(UserRole.OFFICER)){
    authorValidator.validate(Permission.AUTHORIZE_USER);
    user.setUserRole(vo.getUserRole());
}
```

Sau đó mới kiểm tra:

```java
if (user == null) {
    throw new IllegalArgumentException("User cần cập nhật không tồn tại!");
}
```

Nếu `user == null`, method có thể lỗi `NullPointerException` trước khi đi tới đoạn check.

Đề xuất:

- Check `user == null` ngay đầu method.
- Validate input trước khi mutate object.
- Chỉ update entity sau khi mọi validation đã pass.

### 7. Bug ngày mượn trong `BorrowService.setBorrowCard()`

Code có xử lý nếu `vo.getBorrowDate() == null`:

```java
if (vo.getBorrowDate() == null){
    borrowCard.setBorrowDate(Constants.TODAY);
} else {
    borrowCard.setBorrowDate(vo.getBorrowDate());
}
```

Nhưng sau đó lại dùng:

```java
borrowCard.setDueDate(vo.getBorrowDate().plusMonths(1));
```

Nếu `vo.getBorrowDate()` null thì vẫn lỗi.

Đề xuất:

```java
borrowCard.setDueDate(borrowCard.getBorrowDate().plusMonths(1));
```

### 8. Service tự `new` dependency nên khó test

Nhiều service đang tự tạo repository, validator, service khác:

```java
BookRepository bookRepository = new BookRepository();
private static final AuthorService authorService = new AuthorService();
Validator<BookVO> bookValidator = new BookValidator();
```

Điều này làm khó viết unit test vì không thể thay repository giả/mock. Cũng làm service bị gắn cứng với cách lưu dữ liệu hiện tại.

Đề xuất:

- Thêm constructor nhận dependency.
- Có thể giữ constructor mặc định để không phải sửa menu ngay.

Ví dụ:

```java
public BookService(
        BookRepository bookRepository,
        AuthorService authorService,
        Validator<BookVO> bookValidator,
        SessionService sessionService
) {
    this.bookRepository = bookRepository;
    this.authorService = authorService;
    this.bookValidator = bookValidator;
    this.sessionService = sessionService;
}
```

## Cấu trúc đề xuất

Nên tách trách nhiệm như sau:

- `AuthenService`: login, logout, đổi mật khẩu, quản lý thông tin đăng nhập.
- `SessionService` hoặc `AuthContext`: lưu user hiện tại/current user id.
- `AuthorService`: kiểm tra quyền theo user và permission, không in console.
- `BookService`: nghiệp vụ sách.
- `ReaderCardService`: nghiệp vụ độc giả.
- `UserService`: nghiệp vụ người dùng.
- `BorrowService`: nghiệp vụ mượn/trả sách.
- `DashboardService`: nghiệp vụ thống kê.
- `Validator`: chỉ validate dữ liệu đầu vào, không xử lý UI.
- Menu/controller: nhận input từ người dùng, gọi service, in kết quả.

## Thứ tự ưu tiên sửa

1. Bỏ phụ thuộc `test.MainMenuTest` khỏi các service.
2. Sửa cơ chế lấy user hiện tại, không cache `currentUser` trong field.
3. Sửa `checkLogin()` và logout trong `AuthenService`.
4. Chuẩn hóa kiểm tra quyền cho tất cả public method.
5. Tách `Scanner` và `System.out.println` khỏi service.
6. Thêm constructor injection để dễ test.
7. Sửa các bug cụ thể trong `UserService.updateUser()` và `BorrowService.setBorrowCard()`.
