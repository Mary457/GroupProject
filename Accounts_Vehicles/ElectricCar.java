package Accounts_Vehicles;

import Accounts_Vehicles.enums.VehicleType;

public class ElectricCar extends Vehicle {
    private int batteryLevel;

    public ElectricCar(String licensePlate) {
        super(licensePlate, VehicleType.ELECTRIC_CAR);
        this.batteryLevel = 100;
    }

    public ElectricCar(String licensePlate, int batteryLevel) {
        super(licensePlate, VehicleType.ELECTRIC_CAR);
        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new IllegalArgumentException("Battery level must be between 0 and 100");
        }
        this.batteryLevel = batteryLevel;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new IllegalArgumentException("Battery level must be between 0 and 100");
        }
        this.batteryLevel = batteryLevel;
    }

    public void charge(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Charge amount cannot be negative");
        }
        int newLevel = this.batteryLevel + amount;
        this.batteryLevel = Math.min(100, newLevel);
        System.out.println("Charging electric car. Battery level now: " + batteryLevel + "%");
    }

    public boolean needsCharging() {
        return batteryLevel < 20;
    }

    @Override
    public String toString() {
        return "ElectricCar{" +
                "licensePlate='" + getLicensePlate() + '\'' +
                ", batteryLevel=" + batteryLevel +
                '}';
    }
}