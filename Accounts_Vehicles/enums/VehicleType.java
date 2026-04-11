package Accounts_Vehicles.enums;

public enum VehicleType {
    CAR("Car"),
    TRUCK("Truck"),
    VAN("Van"),
    MOTORCYCLE("Motorcycle"),
    ELECTRIC_CAR("ElectricCar");

    private final String value;

    VehicleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
