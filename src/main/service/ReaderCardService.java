package main.service;

import main.constants.Constants;
import main.constants.ReaderConstants;
import main.enums.Gender;
import main.enums.Permission;
import main.info.card.ReaderCard;
import main.info.user.User;
import main.repositories.ReaderRepository;
import main.validate.AuthorValidator;
import main.validate.ReaderValidator;
import main.validate.Validator;
import main.vo.ReaderDetailVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static test.MainMenuTest.userRepository;

/**
 * Chức năng 2: Quản lý độc giả
 *
 * @author Thu Phương
 * @version 1.0
 * @since 2026-05-10
 */
public class ReaderCardService {
    /**
     * Repository để thao tác với dữ liệu độc giả
     */
    ReaderRepository readerRepository = new ReaderRepository();

    /**
     * Service xử lý phân quyền
     */
    private static AuthorService authorService = new AuthorService();

    /**
     * instance variable: người dùng sau khi đăng nhập
     */
    private User currentUser = userRepository.findByUserId(AuthenService.USER_ID);

    /**
     * Validate check quyền truy cập chức năng
     */
    Validator<Permission> authorValidator = new AuthorValidator(currentUser, authorService);

    /**
     * Validate đầu vào chức năng độc giả
     */
    Validator<ReaderDetailVO> readerValidator = new ReaderValidator();

    UserService userService = new UserService();
    /**
     * Quyền truy cập class InfoUpdateService
     */
    private static final Permission PERMISSION_OF_FUNCTION = Permission.MANAGE_USER;

    public List<ReaderCard> showReaderList() {
        authorValidator.validate(PERMISSION_OF_FUNCTION);
        return readerRepository.getAllReaders();
    }


    /**
     * 2.2 Thêm độc giả
     *
     * @param vo
     * @return readerCard
     */
    public ReaderCard addReader(ReaderDetailVO vo) {
        authorValidator.validate(PERMISSION_OF_FUNCTION);
        List<String> errorList = new ArrayList<>();
        errorList.addAll(readerValidator.validate(vo));

        if (!errorList.isEmpty()) {
            System.out.println("Không thể tạo độc giả mới do lỗi sau:");
            for (String error : errorList) {
                System.out.println("- " + error);
            }
            return null;
        }

        ReaderCard readerCard = new ReaderCard();

        String readerId = "READER" + readerRepository.countAllReader();
        readerCard.setReaderId(readerId);

        if (vo.getFullName() != null && !vo.getFullName().isEmpty()) {
            readerCard.setFullName(vo.getFullName());
        }

        // Check IdCard da ton tai
        if (vo.getIdCard() != null && !vo.getIdCard().isEmpty()) {
            readerCard.setIdCard(vo.getIdCard());
        }

        if (!vo.getBirthDate().equals(Constants.INIT_DATE)) {
            readerCard.setBirthDate(vo.getBirthDate());
        }

        if (!vo.getGender().equals(Gender.OTHER)) {
            readerCard.setGender(vo.getGender());
        }

        if (!vo.getEmail().equals(Constants.INIT_STRING)) {
            readerCard.setEmail(vo.getEmail());
        }

        if (!vo.getAddress().equals(Constants.INIT_STRING)) {
            readerCard.setAddress(vo.getAddress());
        }

        if (!vo.getStartDate().equals(ReaderConstants.TODAY)) {
            readerCard.setStartDate(vo.getStartDate());
        }

        readerCard.setEndDate(
                readerCard.getStartDate().plusMonths(
                        ReaderConstants.PLUS_48_MONTHS
                )
        );

        readerRepository.saveReaderCartToFile(readerCard);

        return readerCard;

    }


}
