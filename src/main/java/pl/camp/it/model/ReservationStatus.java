package pl.camp.it.model;

public enum ReservationStatus {
    WAITING("Oczekująca"),
    CANCELED("Anulowana"),
    ACCEPTED("Zaakceptowana"),
    DECLINED("Odrzucona");

    private final String displayValue;

    ReservationStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
