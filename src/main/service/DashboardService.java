package main.service;

import main.enums.Gender;
import main.enums.Genre;
import main.repository.BookRepository;
import main.repository.BorrowDetailRepository;
import main.repository.ReaderRepository;

public class DashboardService {
    private final BookRepository bookRepository = new BookRepository();
    private final ReaderRepository readerRepository = new ReaderRepository();
    private final BorrowDetailRepository borrowDetailRepository = new BorrowDetailRepository();

    /**
     * 6.1 Thống kê số lượng sách trong thư viện
     * @return
     */
    public int bookStats() {
        int sum = 0;
        for (int i = 0; i < bookRepository.getAll().size(); i++) {
            System.out.println("Sách thứ " + (i + 1) + ": " + bookRepository.getAll().get(i).getName() + " - số lượng" + bookRepository.getAll().get(i).getTotal());
            sum += bookRepository.getAll().get(i).getTotal();
        }
        return sum;
    }

    /**
     * 6.2 Thống kê số lượng sách theo thể loại
     * @param genre
     * @return
     */
    public int bookStatsByGenre(Genre genre) {
        int sum = 0;
        for (int i = 0; i < bookRepository.list(null, genre).size(); i++){
            System.out.println("Sách thứ " + (i + 1) + ": " + bookRepository.getAll().get(i).getName() + " - số lượng" + bookRepository.getAll().get(i).getTotal());
            sum += bookRepository.list(null, genre).get(i).getTotal();
        }
        return sum;
    }

    /**
     * 6.3 Thống kê số lượng độc giả
     * @return
     */
    public int readerStats(){
        return readerRepository.getAll().size();
    }

    /**
     * 6.4 Thống kê số lượng độc giả theo giới tính
     * @param gender
     * @return
     */
    public int readerStatsByGender(Gender gender){
        return readerRepository.list(null, gender).size();
    }

    /**
     * 6.5 Thống kê số sách đang được mượn
     * @return
     */
    public int borrowStats(){
        return borrowDetailRepository.list(null, Boolean.FALSE, null).size();
    }

    /**
     * 6.6 Thống kê danh sách độc giả bị trễ hạn
     * @return
     */
    public int lateReturnStats(){
        return borrowDetailRepository.list(null, null, Boolean.TRUE).size();
    }

}
