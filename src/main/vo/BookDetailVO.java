package main.vo;

import main.constants.BookConstants;
import main.constants.Constants;
import main.enums.Genre;

public class BookDetailVO {
    private String isbn = Constants.INIT_STRING;
    private String name = Constants.INIT_STRING;
    private String author = Constants.INIT_STRING;
    private String publisher = Constants.INIT_STRING;
    private String publishYear = BookConstants.INIT_PUBLISH_YEAR;
    private Genre genre = Genre.OTHER;
    private Long price = Constants.DEFAULT_LONG;
    private Integer total = Constants.INT_0;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(String priceStr) {
        if (priceStr == null || priceStr.trim().isEmpty()) {
            this.price = Constants.DEFAULT_LONG;
            return;
        }

        try {
            this.price = Long.parseLong(priceStr.trim());
        } catch (NumberFormatException e) {
            System.out.println("Định dạng giá tiền không hợp lệ: " + priceStr);
            System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
            this.price = Constants.DEFAULT_LONG;
        }
    }

    // Overload thêm hàm setPrice nhận số Long trực tiếp nếu cần
    public void setPrice(Long price) {
        this.price = price != null ? price : Constants.DEFAULT_LONG;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(String totalStr) {
        if (totalStr == null || totalStr.trim().isEmpty()) {
            this.total = Constants.INT_0;
            return;
        }

        try {
            this.total = Integer.parseInt(totalStr.trim());
        } catch (NumberFormatException e) {
            System.out.println("Định dạng số lượng sách không hợp lệ: " + totalStr);
            System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
            // Sử dụng giá trị mặc định nếu parse lỗi
            this.total = Constants.INT_0;
        }
    }

    public void setTotal(Integer total) {
        this.total = total != null ? total : Constants.INT_0;
    }
}