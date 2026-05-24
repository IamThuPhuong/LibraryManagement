package test.service;

import main.constants.Constants;
import main.enums.Gender;
import main.entity.ReaderCard;
import main.service.ReaderCardService;
import main.vo.ReaderVO;

import java.io.IOException;
import java.util.List;

import static test.service.AuthenServiceTest.input;

public class ReaderCardServiceTest {
    ReaderCardService readerCardService = new ReaderCardService();

    public void showReaderList() {
        for (ReaderCard reader : readerCardService.showReaderList()) {
            System.out.println(reader.toString());
        }
    }

    public void addReader() {

        // Form nhập thông tin độc giả
        ReaderVO readerVO = new ReaderVO();
        System.out.println("Form nhập thông tin độc giả:");
        System.out.print("1. Họ và tên (*):");
        String fullName = input.nextLine();
        while (fullName.isEmpty()) {
            System.out.println("Họ tên không được để trống! Vui lòng nhập lại:");
            fullName = input.nextLine();
        }
        System.out.print("\n2. Số căn cước công dân (*):");
        String idCard = input.nextLine();
        while (idCard.isEmpty()) {
            System.out.println("Căn cước không được để trống! Vui lòng nhập lại:");
            idCard = input.nextLine();
        }
        System.out.print("\n3. BirthDay (dd/MM/yyyy):");
        String birthDay = input.nextLine();
        System.out.print("\n4. Gender (Chose: 1.Male /2.Female):");
        String gender = input.nextLine();
        Gender genderEnum = switch (gender) { // Sử dụng switch expression của Java 14+ để gán giá trị enum
            case "1" -> Gender.MALE;
            case "2" -> Gender.FEMALE;
            default -> Gender.OTHER;
        };
        System.out.print("\n5. Email:");
        String email = input.nextLine();
        System.out.print("\n6. Address:");
        String address = input.nextLine();
        System.out.println("\n7. Ngày bắt đầu đăng ký \n(Nếu ngày tạo là hôm nay thì bỏ qua): ");
        String startDate = input.nextLine();


        // Set thông tin độc giả từ form vào ReaderDetailVO
        readerVO.setFullName(fullName);
        readerVO.setIdCard(idCard);
        readerVO.setBirthDay(birthDay);
        readerVO.setAddress(address);
        readerVO.setGender(genderEnum);
        readerVO.setEmail(email);
        readerVO.setAddress(address);
        readerVO.setStartDate(startDate);

        // Service
        readerCardService.createReader(readerVO);
        System.out.println("==Kết thúc tiến trình tạo độc giả==!");
    }


    /**
     * Cập nhật user phía màn hình
     */
    public void updateReader() throws IOException {
        while (true) {
            System.out.print("=========DANH SÁCH ĐỘC GIẢ===========");
            List<ReaderCard> readerList = readerCardService.showReaderList();
            for (int i = 0; i < readerList.size(); i++) {
                System.out.println(i + 1 + ". " + readerList.get(i).toString());
            }

            System.out.println("Chọn người muốn update (Từ 1 đến " + readerList.size() + "). Chọn 0 để thoát.");
            int readerNo = input.nextInt();

            if(readerNo == Constants.INT_0){
                input.nextLine();
                return;
            }

            input.nextLine();
            ReaderCard readerCard = readerList.get(readerNo - 1);

            ReaderVO vo = new ReaderVO();
            String chosenInfo;
            do {
                System.out.println("=========THÔNG TIN ĐỘC GIẢ===========");
                System.out.println("1. Họ và tên: " + readerCard.getFullName());
                System.out.println("2. Số CCCD: " + readerCard.getIdCard());
                System.out.println("3. Ngày sinh: " + readerCard.getBirthDate());
                System.out.println("4. Giới tính: " + readerCard.getGender());
                System.out.println("5. Email: " + readerCard.getEmail());
                System.out.println("6. Địa chỉ: " + readerCard.getAddress());
                System.out.println("7. Ngày bắt đầu đăng ký" + readerCard.getStartDate());
                System.out.println("Chọn thông tin muốn cập nhật (1-7), 0 để hoàn tất update, 9 để quay lại, 99 để thoát hẳn:");

                chosenInfo = input.nextLine();


                switch (chosenInfo) {
                    case "1":
                        System.out.println("Nhập họ tên:");
                        String newFullName = input.nextLine();
                        vo.setFullName(newFullName);
                        break;
                    case "2":
                        System.out.println("Nhập số CCCD:");
                        String newIDCardNo = input.nextLine();
                        vo.setIdCard(newIDCardNo);
                        break;
                    case "3":
                        System.out.println("Nhập ngày sinh (dd/MM/yyyy):");
                        String newBirthDate = input.nextLine();
                        vo.setBirthDay(newBirthDate);
                        break;
                    case "4":
                        System.out.println("Chọn giới tính mới (1.Male /2.Female):");
                        String newGender = input.nextLine();
                        switch (newGender) {
                            case "1":
                                readerCard.setGender(Gender.MALE);
                                break;
                            case "2":
                                readerCard.setGender(Gender.FEMALE);
                                break;
                            default:
                                readerCard.setGender(Gender.OTHER);
                        }
                        vo.setGender(readerCard.getGender());
                        break;
                    case "5":
                        System.out.println("Nhập email:");
                        String newEmail = input.nextLine();
                        vo.setEmail(newEmail);
                        break;
                    case "6":
                        System.out.println("Nhập địa chỉ:");
                        String newAddress = input.nextLine();
                        vo.setAddress(newAddress);
                        break;
                    case "7":
                        System.out.println("Nhập ngày bắt đầu đăng ký (dd/MM/yyyy):");
                        String newStartDate = input.nextLine();
                        vo.setStartDate(newStartDate);
                        break;
                }

            } while (!chosenInfo.equals("0") && !chosenInfo.equals("99") && !chosenInfo.equals("9"));

            if (chosenInfo.equals("9")) {
                continue;
            }

            if (chosenInfo.equals("99")) {
                break;
            }


            ReaderCardService readerService = new ReaderCardService();
            readerService.updateReader(readerCard, vo);

            System.out.println("Cập nhật thông tin thành công! Thông tin mới:");
            System.out.println("Họ và tên: " + readerCard.getFullName());
            System.out.println("Số căn cước: " + readerCard.getIdCard());
            System.out.println("Ngày sinh: " + readerCard.getBirthDate());
            System.out.println("Giới tính: " + readerCard.getGender());
            System.out.println("Email: " + readerCard.getEmail());
            System.out.println("Address: " + readerCard.getAddress());
            System.out.println("Ngày đăng ký: " + readerCard.getStartDate());
        }
    }

    public void findReaderByFullName(){
        System.out.println("Nhập tên tìm kiếm (Không dấu):");
        String name = input.nextLine();
        List<ReaderCard> foundReaders = readerCardService.findReaderByFullName(name);
        if (foundReaders == null){
            System.out.println("Không tìm thấy độc giả nào");
            return;
        }
        System.out.println("Kết quả tìm kiếm:");
        for (int i = 0; i < foundReaders.size(); i++){
            System.out.println(i + ". " + foundReaders.get(i).toString());
        }
    }

    public void findReaderByIdCardNo(){
        System.out.println("Nhập số CCCD:");
        String cccd = input.nextLine();
        ReaderCard foundReader = readerCardService.findReaderByIdCardNo(cccd);
        if (foundReader == null){
            System.out.println("Không tồn tại độc giả này");
            return;
        }
        System.out.println("Kết quả tìm kiếm:");
        System.out.println(foundReader);
    }
}
