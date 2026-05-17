package main.enums;

public enum Genre {
    FICTION("Tiểu thuyết"),
    SELF_HELP("Kỹ năng sống"),
    CLASSIC_LITERATURE("Văn học cổ điển"),
    SCIENCE_TECH("Khoa học - Công nghệ"),
    BUSINESS_ECONOMICS("Kinh tế - Kinh doanh"),
    HISTORY_GEOGRAPHY("Lịch sử - Địa lý"),
    CHILDREN("Truyện thiếu nhi"),
    COMIC_MANGA("Truyện tranh"),
    OTHER("Khác");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    // tiếng Việt
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Hàm tiện ích giúp parse từ String (trong file CSV hoặc Input) sang Enum Genre.
     */
    public static Genre fromString(String text) {
        if (text == null || text.trim().isEmpty()) {
            return OTHER;
        }

        String trimmedText = text.trim();
        for (Genre genre : Genre.values()) {
            if (genre.displayName.equalsIgnoreCase(trimmedText) || genre.name().equalsIgnoreCase(trimmedText)) {
                return genre;
            }
        }

        return OTHER;
    }
}