package Accounts_Vehicles;

import Accounts_Vehicles.enums.VehicleType;

public class Motorcycle extends Vehicle {
    private boolean hasSidecar;

    public Motorcycle(String licensePlate) {
        super(licensePlate, VehicleType.MOTORCYCLE);
        this.hasSidecar = false;
    }

    public Motorcycle(String licensePlate, boolean hasSidecar) {
        super(licensePlate, VehicleType.MOTORCYCLE);
        this.hasSidecar = hasSidecar;
    }

    public boolean hasSidecar() {
        return hasSidecar;
    }

    public void setHasSidecar(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }

    @Override
    public String toString() {
        return "Motorcycle{licensePlate='" + getLicensePlate() + "', hasSidecar=" + hasSidecar + "}";
    }
}