package Accounts_Vehicles;

import Accounts_Vehicles.enums.VehicleType;

public class Truck extends Vehicle {
    private double cargoCapacity;

    public Truck(String licensePlate) {
        super(licensePlate, VehicleType.TRUCK);
        this.cargoCapacity = 0;
    }

    public Truck(String licensePlate, double cargoCapacity) {
        super(licensePlate, VehicleType.TRUCK);
        this.cargoCapacity = cargoCapacity;
    }

    public double getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(double cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "licensePlate='" + getLicensePlate() + '\'' +
                ", cargoCapacity=" + cargoCapacity +
                '}';
    }
}