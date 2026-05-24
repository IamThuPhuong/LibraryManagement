package test.service;

import main.service.DashboardService;

public class DashboardServiceTest {
    public void testShowDashboard() {
        System.out.println("Test showDashboard");
        System.out.println("6.1 Thống kê số lượng sách trong thư viện");
        DashboardService dashboardService = new DashboardService();
        int totalBooks = dashboardService.bookStats();
        System.out.println("Tổng số lượng sách trong thư viện: " + totalBooks);
            System.out.println("6.2 Thống kê số lượng sách theo thể loại");
        int totalFictionBooks = dashboardService.bookStatsByGenre(main.enums.Genre.FICTION);
        System.out.println("Tổng số lượng sách thể loại Fiction: " + totalFictionBooks);
        System.out.println("6.3 Thống kê số lượng độc giả");
        int totalReaders = dashboardService.readerStats();
        System.out.println("Tổng số lượng độc giả: " + totalReaders);
        System.out.println("6.4 Thống kê số lượng độc giả theo giới tính");
        int totalMaleReaders = dashboardService.readerStatsByGender(main.enums.Gender.MALE);
        System.out.println("Tổng số lượng độc giả nam: " + totalMaleReaders);
        System.out.println("6.5 Thống kê số sách đang được mượn");
        int totalBorrowedBooks = dashboardService.borrowStats();
        System.out.println("Tổng số lượng sách đang được mượn: " + totalBorrowedBooks);
        System.out.println("6.6 Thống kê số sách quá hạn");
        int totalLateBooks = dashboardService.lateReturnStats();
        // TODO: sửa lại thống kê số lượng sách quá hạn không count đúng OVERDUE do count bằng ngày trả
        System.out.println("Tổng số lượng sách quá hạn: " + totalLateBooks);

    }
}
