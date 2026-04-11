package Accounts_Vehicles.enums;

public enum AccountType {
    ADMIN("Admin"),
    PARKING_ATTENDANT("ParkingAttendant");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
