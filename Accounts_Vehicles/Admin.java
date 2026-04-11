package Accounts_Vehicles;

import Accounts_Vehicles.enums.AccountType;
import Infrastructure.ParkingFloor;
import Infrastructure.ParkingLot;
import Infrastructure.ParkingSpot;
import Tickets_Payments.ParkingRate;

public class Admin extends Account {

    public Admin(String accountId, String name, String email, String password) {
        super(accountId, name, email, password, AccountType.ADMIN);
    }

    @Override
    public String getRoleDescription() {
        return "Admin - Can manage parking floors, spots, and rates";
    }

    public void addParkingFloor(ParkingLot parkingLot, ParkingFloor floor) {
        if (parkingLot == null) {
            throw new IllegalArgumentException("ParkingLot cannot be null");
        }
        if (floor == null) {
            throw new IllegalArgumentException("ParkingFloor cannot be null");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Admin must be logged in to perform this action");
        }
        parkingLot.addFloor(floor);
        System.out.println("Admin " + name + " added floor " + floor.getFloorId());
    }

    public void removeParkingFloor(ParkingLot parkingLot, String floorId) {
        if (parkingLot == null) {
            throw new IllegalArgumentException("ParkingLot cannot be null");
        }
        if (floorId == null || floorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Floor ID cannot be null or empty");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Admin must be logged in to perform this action");
        }
        parkingLot.removeFloor(floorId);
        System.out.println("Admin " + name + " removed floor " + floorId);
    }

    public void addParkingAttendant(ParkingService service, ParkingAttendant attendant) {
        if (service == null) {
            throw new IllegalArgumentException("ParkingService cannot be null");
        }
        if (attendant == null) {
            throw new IllegalArgumentException("ParkingAttendant cannot be null");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Admin must be logged in to perform this action");
        }
        service.addAttendant(attendant);
        System.out.println("Admin " + name + " added attendant " + attendant.getName());
    }

    public void removeParkingAttendant(ParkingService service, String attendantId) {
        if (service == null) {
            throw new IllegalArgumentException("ParkingService cannot be null");
        }
        if (attendantId == null || attendantId.trim().isEmpty()) {
            throw new IllegalArgumentException("Attendant ID cannot be null or empty");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Admin must be logged in to perform this action");
        }
        service.removeAttendant(attendantId);
        System.out.println("Admin " + name + " removed attendant " + attendantId);
    }

    public void updateParkingRate(ParkingRate rateSystem, int hour, double newRate) {
        if (rateSystem == null) {
            throw new IllegalArgumentException("ParkingRate cannot be null");
        }
        if (hour < 1 || hour > 24) {
            throw new IllegalArgumentException("Hour must be between 1 and 24");
        }
        if (newRate < 0) {
            throw new IllegalArgumentException("Rate cannot be negative");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Admin must be logged in to perform this action");
        }
        rateSystem.updateRate(hour, newRate);
        System.out.println("Admin " + name + " updated hour " + hour + " rate to $" + newRate);
    }

    public void addParkingSpot(ParkingFloor floor, ParkingSpot spot) {
        if (floor == null) {
            throw new IllegalArgumentException("ParkingFloor cannot be null");
        }
        if (spot == null) {
            throw new IllegalArgumentException("ParkingSpot cannot be null");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Admin must be logged in to perform this action");
        }
        floor.addSpot(spot);
        System.out.println("Admin " + name + " added spot " + spot.getSpotId());
    }

    public void removeParkingSpot(ParkingFloor floor, String spotId) {
        if (floor == null) {
            throw new IllegalArgumentException("ParkingFloor cannot be null");
        }
        if (spotId == null || spotId.trim().isEmpty()) {
            throw new IllegalArgumentException("Spot ID cannot be null or empty");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Admin must be logged in to perform this action");
        }
        floor.removeSpot(spotId);
        System.out.println("Admin " + name + " removed spot " + spotId);
    }
}
