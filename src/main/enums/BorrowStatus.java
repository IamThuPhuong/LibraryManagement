package main.enums;

public enum BorrowStatus {
    BORROWING("Đang mượn"),
    RETURNED("Đã trả"),
    OVERDUE("Quá hạn");

    private final String displayName;

    BorrowStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static BorrowStatus fromString(String text) {
        if (text == null || text.trim().isEmpty()) {
            return BORROWING;
        }

        String trimmedText = text.trim();
        for (BorrowStatus borrowStatus : BorrowStatus.values()) {
            if (borrowStatus.displayName.equalsIgnoreCase(trimmedText) || borrowStatus.name().equalsIgnoreCase(trimmedText)) {
                return borrowStatus;
            }
        }

        return BORROWING;
    }
}
