package pl.camp.it.model;

public enum RestaurantStatus {
    ACTIVE("Aktywna"),
    OFF("Nieaktywna");

    private final String displayValue;

    RestaurantStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
