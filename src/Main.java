import main.info.user.Gender;
import main.info.user.Status;
import main.info.user.User;
import main.service.AuthorService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();

        Calendar cal = Calendar.getInstance();

        cal.set(2000, 0, 1);
        users.add(new User("123","user1", "123", "Nguyen Van A", cal.getTime(), "001", "HCM", Gender.MALE, Status.ACTIVATED));

        cal.set(1999, 1, 2);
        users.add(new User("456","user2", "123", "Tran Thi B", cal.getTime(), "002", "HN", Gender.FEMALE, Status.ACTIVATED));

        cal.set(1998, 2, 3);
        users.add(new User("789","user3", "123", "Le Van C", cal.getTime(), "003", "DN", Gender.MALE, Status.BLOCK));

        cal.set(1997, 3, 4);
        users.add(new User("111","user4", "123", "Pham Thi D", cal.getTime(), "004", "CT", Gender.FEMALE, Status.ACTIVATED));

        cal.set(1996, 4, 5);
        users.add(new User("112","user5", "123", "Hoang Van E", cal.getTime(), "005", "HP", Gender.MALE, Status.ACTIVATED));

        cal.set(1995, 5, 6);
        users.add(new User("113","user6", "123", "Do Thi F", cal.getTime(), "006", "BD", Gender.FEMALE, Status.BLOCK));

        cal.set(1994, 6, 7);
        users.add(new User("114","user7", "123", "Vu Van G", cal.getTime(), "007", "QN", Gender.MALE, Status.ACTIVATED));

        cal.set(1993, 7, 8);
        users.add(new User("115","user8", "123", "Dang Thi H", cal.getTime(), "008", "NT", Gender.FEMALE, Status.ACTIVATED));

        cal.set(1992, 8, 9);
        users.add(new User("116","user9", "123", "Bui Van I", cal.getTime(), "009", "VT", Gender.MALE, Status.BLOCK));

        cal.set(1991, 9, 10);
        users.add(new User("117","user10", "123", "Ngo Thi K", cal.getTime(), "010", "LA", Gender.FEMALE, Status.ACTIVATED));

        // In thử
        users.forEach(u -> System.out.println(u.getUserName() + " - " + u.getFullName()));

        AuthorService testAuthor = new AuthorService();
        testAuthor.loginService(users);

    }
}