package pl.camp.it.model;

public enum PromotionStatus {
    ACTIVE("Aktywna"),
    OFF("Nieaktywna");

    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

    PromotionStatus(String displayValue) {
        this.displayValue = displayValue;
    }
}
