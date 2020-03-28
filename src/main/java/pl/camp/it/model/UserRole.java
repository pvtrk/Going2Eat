package pl.camp.it.model;

public enum UserRole {
    GUEST("Gość"),
    USER("Użytkownik"),
    RESTORER("Restaurator"),
    ADMIN("Admin");

    private final String displayValue;

    UserRole(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
