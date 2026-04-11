package Accounts_Vehicles;

import Accounts_Vehicles.enums.VehicleType;

public class Van extends Vehicle {
    private int passengerCapacity;

    public Van(String licensePlate) {
        super(licensePlate, VehicleType.VAN);
        this.passengerCapacity = 0;
    }

    public Van(String licensePlate, int passengerCapacity) {
        super(licensePlate, VehicleType.VAN);
        if (passengerCapacity < 0) {
            throw new IllegalArgumentException("Passenger capacity cannot be negative");
        }
        this.passengerCapacity = passengerCapacity;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        if (passengerCapacity < 0) {
            throw new IllegalArgumentException("Passenger capacity cannot be negative");
        }
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public String toString() {
        return "Van{" +
                "licensePlate='" + getLicensePlate() + '\'' +
                ", passengerCapacity=" + passengerCapacity +
                '}';
    }
}
