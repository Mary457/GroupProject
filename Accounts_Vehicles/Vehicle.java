package Accounts_Vehicles;

import Accounts_Vehicles.enums.VehicleType;
import Infrastructure.ParkingSpot;

public abstract class Vehicle {
    protected String licensePlate;
    protected VehicleType vehicleType;
    protected ParkingSpot assignedSpot;

    public Vehicle(String licensePlate, VehicleType vehicleType) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.assignedSpot = null;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public ParkingSpot getAssignedSpot() {
        return assignedSpot;
    }

    public void setAssignedSpot(ParkingSpot assignedSpot) {
        this.assignedSpot = assignedSpot;
    }

    public String getRequiredSpotType() {
        switch (vehicleType) {
            case CAR:
                return "COMPACT";
            case TRUCK:
            case VAN:
                return "LARGE";
            case MOTORCYCLE:
                return "MOTORCYCLE";
            case ELECTRIC_CAR:
                return "ELECTRIC";
            default:
                return "COMPACT";
        }
    }

    public boolean isParked() {
        return assignedSpot != null;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "licensePlate='" + licensePlate + '\'' +
                ", vehicleType=" + vehicleType +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehicle vehicle = (Vehicle) obj;
        return licensePlate.equals(vehicle.licensePlate);
    }

    @Override
    public int hashCode() {
        return licensePlate.hashCode();
    }
}