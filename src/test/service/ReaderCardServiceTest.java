package test.service;

import main.enums.Gender;
import main.info.card.ReaderCard;
import main.service.ReaderCardService;
import main.vo.ReaderDetailVO;

import java.io.IOException;

import static test.service.AuthenServiceTest.input;

public class ReaderCardServiceTest {
    ReaderCardService readerCardService = new ReaderCardService();

    public void showReaderList(){
        for (ReaderCard reader : readerCardService.showReaderList()){
            System.out.println(reader.toString());
        }
    }
    public void addReader (){

        // Form nhập thông tin độc giả
        ReaderDetailVO readerDetailVO = new ReaderDetailVO();
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
        readerDetailVO.setFullName(fullName);
        readerDetailVO.setIdCard(idCard);
        readerDetailVO.setBirthDay(birthDay);
        readerDetailVO.setAddress(address);
        readerDetailVO.setGender(genderEnum);
        readerDetailVO.setEmail(email);
        readerDetailVO.setAddress(address);
        readerDetailVO.setStartDate(startDate);

        // Service
        readerCardService.createReader(readerDetailVO);
        System.out.println("Tạo độc giả thành công!");
    }

    // TODO: Test 2.3 cập nhật thông tin độc giả
    /**
     * Cập nhật user phía màn hình
     */
    public void updateReader(ReaderCard readerCard) throws IOException {

        //====[START] MAN HINH
        ReaderDetailVO vo = new ReaderDetailVO();
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

            System.out.println("Chọn thông tin muốn cập nhật (1-7), hoặc 0 để thoát:");

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

        } while (!chosenInfo.equals("0"));

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
