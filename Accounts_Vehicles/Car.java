package Accounts_Vehicles;

import Accounts_Vehicles.enums.VehicleType;

public class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, VehicleType.CAR);
    }

    @Override
    public String toString() {
        return "Car{licensePlate='" + getLicensePlate() + "'}";
    }
}