package Accounts_Vehicles.enums;

public enum SpotType {
    COMPACT("Compact"),
    LARGE("Large"),
    HANDICAPPED("Handicapped"),
    MOTORCYCLE("Motorcycle"),
    ELECTRIC("Electric");

    private final String value;

    SpotType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}