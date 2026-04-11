package Accounts_Vehicles.enums;

public enum TicketStatus {
    ACTIVE("Active"),
    PAID("Paid"),
    LOST("Lost");

    private final String value;

    TicketStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}