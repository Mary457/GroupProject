package Accounts_Vehicles.enums;

public enum PaymentMethod {
    CASH("Cash"),
    CREDIT_CARD("CreditCard");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
