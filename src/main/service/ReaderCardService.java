package main.service;

import main.constants.Constants;
import main.constants.ReaderConstants;
import main.enums.Gender;
import main.enums.Permission;
import main.entity.ReaderCard;
import main.entity.User;
import main.repository.ReaderRepository;
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
        readerCard.setIdCard(vo.getIdCard());

        if (!vo.getBirthDate().isEqual(Constants.INIT_DATE)) {
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

        if (!vo.getStartDate().isEqual(Constants.INIT_DATE)) {
            readerCard.setStartDate(vo.getStartDate());
        } else {
            readerCard.setStartDate(Constants.TODAY);
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

        if(!Constants.INIT_STRING.equals(vo.getFullName())) {
            reader.setFullName(vo.getFullName());
        }
        if(!Gender.OTHER.equals(vo.getGender())) {
            reader.setGender(vo.getGender());
        }
        if(!Constants.INIT_STRING.equals(vo.getEmail())) {
            reader.setEmail(vo.getEmail());
        }
        if(!Constants.INIT_STRING.equals(vo.getIdCard())){
            reader.setIdCard(vo.getIdCard());
        }
        if(!Constants.INIT_STRING.equals(vo.getAddress())) {
            reader.setAddress(vo.getAddress());
        }
        if(!Constants.INIT_DATE.isEqual(vo.getBirthDate())) {
            reader.setBirthDate(vo.getBirthDate());
        }
        if(!Constants.INIT_DATE.isEqual(vo.getStartDate())) {
            reader.setStartDate(vo.getStartDate());
            reader.setEndDate(vo.getStartDate().plusMonths(ReaderConstants.PLUS_48_MONTHS));
        }

        readerRepository.updateReaderCard(reader);

        return reader;
    }

    /**
     * 2.4 Xóa thông tin một độc giả
     * @param deleteId
     * @return
     */
    public String deleteReaderCard(String deleteId){
        readerRepository.delete(deleteId);
        return deleteId;
    }

    /**
     * 2.5 Tìm kiếm độc giả theo CMND
     * @param idCardNo
     * @return
     */
    public ReaderCard findReaderByIdCardNo(String idCardNo){
        return readerRepository.findByIdCardNo(idCardNo);
    }

    /**
     * 2.6 Tìm kiếm độc giả theo họ tên
     * @param name
     * @return
     */
    public List<ReaderCard> findReaderByFullName(String name){
        return readerRepository.findByName(name);
    }
}
