package main.service;

import main.constants.Constants;
import main.constants.ReaderConstants;
import main.enums.Gender;
import main.enums.Permission;
import main.info.card.ReaderCard;
import main.info.user.User;
import main.repositories.ReaderRepository;
import main.validate.AuthorValidator;
import main.validate.ReaderUpdateDataValidator;
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
    private static final AuthorService authorService = new AuthorService();

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

    /** Validate update thông tin độc giả */
    Validator<ReaderDetailVO> readerUpdateValidator = new ReaderUpdateDataValidator();

    /** Quyền truy cập class InfoUpdateService */
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
    public ReaderCard createReader(ReaderDetailVO vo) {
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
        readerCard.setFullName(vo.getFullName());

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

    /**
     * 2.3 Chỉnh sửa thông tin một độc giả
     * @param reader
     * @param vo
     * @return readerCard
     */
    public ReaderCard updateReader(ReaderCard reader, ReaderDetailVO vo) throws IllegalArgumentException, ExceptionInInitializerError, IOException {
        authorValidator.validate(PERMISSION_OF_FUNCTION);
        List<String> errorList = new ArrayList<>(readerUpdateValidator.validate(vo));
        if (!errorList.isEmpty()){
            System.out.println("Không thể cập nhật độc giả do có lỗi sau:");
            for (String error : errorList) {
                System.out.println("- " + error);
            }
            return reader;
        }

        if(!vo.getFullName().equals(reader.getFullName())) {
            reader.setFullName(vo.getFullName());
        }
        if(!vo.getGender().equals(reader.getGender())) {
            reader.setGender(vo.getGender());
        }
        if(!vo.getEmail().equals(reader.getEmail())) {
            reader.setEmail(vo.getEmail());
        }
        if(!vo.getIdCard().equals(reader.getReaderId())) {
            reader.setIdCard(vo.getIdCard());
        }
        if(!vo.getAddress().equals(reader.getAddress())) {
            reader.setAddress(vo.getAddress());
        }
        if(!(vo.getBirthDate().isEqual(reader.getBirthDate()))) {
            reader.setBirthDate(vo.getBirthDate());
        }
        if(!(vo.getStartDate().isEqual(reader.getStartDate()))) {
            reader.setStartDate(vo.getStartDate());
        }

        readerRepository.updateReaderCard(reader);

        return reader;
    }




}
