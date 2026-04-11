package Accounts_Vehicles;

import Infrastructure.ParkingLot;
import Infrastructure.ParkingSpot;
import Tickets_Payments.ParkingTicket;
import Infrastructure.ParkingFloor;

import java.util.*;

public class ParkingService {
    private ParkingLot parkingLot;
    private Map<String, ParkingTicket> activeTickets;
    private Map<String, ParkingAttendant> attendants;

    public ParkingService(ParkingLot parkingLot) {
        if (parkingLot == null) {
            throw new IllegalArgumentException("ParkingLot cannot be null");
        }
        this.parkingLot = parkingLot;
        this.activeTickets = new HashMap<>();
        this.attendants = new HashMap<>();
    }

    public void addAttendant(ParkingAttendant attendant) {
        if (attendant == null) {
            throw new IllegalArgumentException("ParkingAttendant cannot be null");
        }
        attendants.put(attendant.getAccountId(), attendant);
        System.out.println("Attendant " + attendant.getName() + " added to system");
    }

    public void removeAttendant(String attendantId) {
        if (attendantId == null || attendantId.trim().isEmpty()) {
            throw new IllegalArgumentException("Attendant ID cannot be null or empty");
        }
        ParkingAttendant removed = attendants.remove(attendantId);
        if (removed != null) {
            System.out.println("Attendant " + removed.getName() + " removed from system");
        } else {
            throw new IllegalArgumentException("Attendant with ID " + attendantId + " not found");
        }
    }

    public ParkingAttendant getAttendant(String attendantId) {
        if (attendantId == null || attendantId.trim().isEmpty()) {
            throw new IllegalArgumentException("Attendant ID cannot be null or empty");
        }
        return attendants.get(attendantId);
    }

    public List<ParkingAttendant> getAllAttendants() {
        return new ArrayList<>(attendants.values());
    }

    public ParkingSpot assignSpot(ParkingTicket ticket, Vehicle vehicle) {
        if (ticket == null) {
            throw new IllegalArgumentException("ParkingTicket cannot be null");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }

        String requiredSpotType = vehicle.getRequiredSpotType();

        for (ParkingFloor floor : parkingLot.getFloors().values()) {
            ParkingSpot spot = floor.getAvailableSpot(requiredSpotType);
            if (spot != null) {
                spot.assignVehicle(vehicle);
                ticket.setAssignedSpot(spot);
                activeTickets.put(ticket.getTicketId(), ticket);
                floor.updateDisplayBoard();
                System.out.println("Assigned spot " + spot.getSpotId() + " to " + vehicle.getLicensePlate());
                return spot;
            }
        }

        throw new IllegalStateException("No available spot for vehicle: " + vehicle.getLicensePlate());
    }

    public void releaseSpot(ParkingTicket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("ParkingTicket cannot be null");
        }

        ParkingSpot spot = ticket.getAssignedSpot();
        if (spot != null) {
            ParkingFloor floor = parkingLot.getFloor(spot.getFloorId());
            if (floor == null) {
                throw new IllegalStateException("Floor not found for spot: " + spot.getSpotId());
            }
            spot.removeVehicle();
            floor.updateDisplayBoard();
            activeTickets.remove(ticket.getTicketId());
            System.out.println("Released spot " + spot.getSpotId());
        } else {
            throw new IllegalStateException("Ticket has no assigned spot");
        }
    }

    public ParkingTicket getActiveTicket(String ticketId) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty");
        }
        return activeTickets.get(ticketId);
    }

    public void removeActiveTicket(String ticketId) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty");
        }
        activeTickets.remove(ticketId);
    }

    public boolean isTicketActive(String ticketId) {
        return activeTickets.containsKey(ticketId);
    }

    public int getActiveTicketsCount() {
        return activeTickets.size();
    }

    public Map<String, Map<String, Integer>> getAvailableSpotsSummary() {
        Map<String, Map<String, Integer>> summary = new HashMap<>();

        for (Map.Entry<String, ParkingFloor> entry : parkingLot.getFloors().entrySet()) {
            String floorId = entry.getKey();
            ParkingFloor floor = entry.getValue();
            Map<String, Integer> available = new HashMap<>();

            for (ParkingSpot spot : floor.getSpots().values()) {
                if (spot.isAvailable()) {
                    String spotType = spot.getSpotType().getValue();
                    available.put(spotType, available.getOrDefault(spotType, 0) + 1);
                }
            }
            summary.put(floorId, available);
        }
        return summary;
    }

    public void displayParkingStatus() {
        System.out.println("\n=== PARKING STATUS ===");
        for (ParkingFloor floor : parkingLot.getFloors().values()) {
            int total = floor.getSpots().size();
            long occupied = floor.getSpots().values().stream().filter(s -> !s.isAvailable()).count();
            System.out.println("Floor " + floor.getFloorId() + ": " + occupied + "/" + total + " occupied");
            floor.getDisplayBoard().show();
        }
        System.out.println("Active Tickets: " + activeTickets.size());
        System.out.println("=====================\n");
    }

    public boolean isParkingFull() {
        return parkingLot.isFull();
    }
}
