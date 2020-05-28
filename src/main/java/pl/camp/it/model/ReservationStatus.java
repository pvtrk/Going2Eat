package pl.camp.it.model;

public enum ReservationStatus implements Comparable<ReservationStatus>{
    WAITING("Oczekująca", 0),
    CANCELED("Anulowana", 2),
    ACCEPTED("Zaakceptowana", 1),
    DECLINED("Odrzucona", 3),
    PAST("Przeszła", 4);

    private final String displayValue;
    private int numberValue;

    ReservationStatus(String displayValue, int numberValue) {
        this.displayValue = displayValue;
        this.numberValue = numberValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public int getNumberValue() {
        return numberValue;
    }
}
