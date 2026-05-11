package test.service;

import main.enums.Gender;
import main.info.card.ReaderCard;
import main.service.ReaderCardService;
import main.vo.ReaderDetailVO;

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

}
