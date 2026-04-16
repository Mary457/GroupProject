package Tickets_Payments;

import Accounts_Vehicles.Vehicle;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class TicketService {
    private Map<String, ParkingTicket> activeTickets;
    private Map<String, ParkingTicket> allTickets;
    private ParkingRate parkingRate;
    private int totalTicketsIssued;

    public TicketService(ParkingRate parkingRate) {
        if (parkingRate == null) {
            throw new IllegalArgumentException("ParkingRate cannot be null");
        }
        this.parkingRate = parkingRate;
        this.activeTickets = new HashMap<>();
        this.allTickets = new HashMap<>();
        this.totalTicketsIssued = 0;
    }

    public ParkingTicket createTicket(Vehicle vehicle, String entryPanelId) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (entryPanelId == null || entryPanelId.trim().isEmpty()) {
            throw new IllegalArgumentException("Entry panel ID cannot be null or empty");
        }

        ParkingTicket ticket = new ParkingTicket(vehicle, entryPanelId);
        activeTickets.put(ticket.getTicketId(), ticket);
        allTickets.put(ticket.getTicketId(), ticket);
        totalTicketsIssued++;

        System.out.println("Ticket created: " + ticket.getTicketId() + " for vehicle " + vehicle.getLicensePlate());
        return ticket;
    }

    public ParkingTicket createTicket(String ticketId, Vehicle vehicle, String entryPanelId) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (entryPanelId == null || entryPanelId.trim().isEmpty()) {
            throw new IllegalArgumentException("Entry panel ID cannot be null or empty");
        }

        ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, entryPanelId);
        activeTickets.put(ticket.getTicketId(), ticket);
        allTickets.put(ticket.getTicketId(), ticket);
        totalTicketsIssued++;

        return ticket;
    }

    public ParkingTicket getTicket(String ticketId) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty");
        }
        return allTickets.get(ticketId);
    }

    public ParkingTicket getActiveTicket(String ticketId) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty");
        }
        return activeTickets.get(ticketId);
    }

    public double calculateFee(String ticketId) {
        ParkingTicket ticket = getTicket(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket not found: " + ticketId);
        }
        return parkingRate.calculateCharge(ticket);
    }

    public double calculateFee(ParkingTicket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("ParkingTicket cannot be null");
        }
        return parkingRate.calculateCharge(ticket);
    }

    public void validateTicket(ParkingTicket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("ParkingTicket cannot be null");
        }

        System.out.println("\nValidating ticket: " + ticket.getTicketId());
        System.out.println("Status: " + ticket.getStatus().getValue());
        System.out.println("Entry Time: " + ticket.getFormattedEntryTime());
        System.out.println("Duration: " + String.format("%.2f", ticket.getDurationHours()) + " hours");

        if (ticket.isPaid()) {
            System.out.println("✓ Ticket is PAID. Customer can exit.");
        } else if (ticket.isActive()) {
            double fee = calculateFee(ticket);
            System.out.println("⚠ Ticket is ACTIVE. Amount due: $" + String.format("%.2f", fee));
        } else if (ticket.isLost()) {
            System.out.println("✗ Ticket is LOST. Customer needs to report to attendant.");
        }

        if (ticket.isExpired()) {
            System.out.println("⚠ WARNING: Ticket has expired!");
        }
    }

    public void closeTicket(String ticketId, String exitPanelId) {
        ParkingTicket ticket = getActiveTicket(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Active ticket not found: " + ticketId);
        }

        if (!ticket.isPaid()) {
            throw new IllegalStateException("Cannot close ticket. Ticket is not paid. Status: " + ticket.getStatus());
        }

        ticket.markExited(exitPanelId);
        activeTickets.remove(ticketId);
        System.out.println("Ticket " + ticketId + " closed successfully.");
    }

    public void reportLostTicket(String ticketId) {
        ParkingTicket ticket = getTicket(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket not found: " + ticketId);
        }
        ticket.markAsLost();
        activeTickets.remove(ticketId);
    }

    public List<ParkingTicket> getAllActiveTickets() {
        return new ArrayList<>(activeTickets.values());
    }

    public List<ParkingTicket> getAllTickets() {
        return new ArrayList<>(allTickets.values());
    }

    public List<ParkingTicket> getTicketsByVehicle(String licensePlate) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }

        List<ParkingTicket> result = new ArrayList<>();
        for (ParkingTicket ticket : allTickets.values()) {
            if (ticket.getVehicleLicensePlate().equalsIgnoreCase(licensePlate)) {
                result.add(ticket);
            }
        }
        return result;
    }

    public int getActiveTicketsCount() {
        return activeTickets.size();
    }

    public int getTotalTicketsIssued() {
        return totalTicketsIssued;
    }

    public double getTotalRevenue() {
        double total = 0.0;
        for (ParkingTicket ticket : allTickets.values()) {
            total += ticket.getAmountPaid();
        }
        return total;
    }

    public boolean isVehicleCurrentlyParked(String licensePlate) {
        for (ParkingTicket ticket : activeTickets.values()) {
            if (ticket.getVehicleLicensePlate().equalsIgnoreCase(licensePlate)) {
                return true;
            }
        }
        return false;
    }

    public void displayActiveTickets() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           ACTIVE TICKETS (" + activeTickets.size() + ")");
        System.out.println("=".repeat(60));

        if (activeTickets.isEmpty()) {
            System.out.println("No active tickets.");
        } else {
            System.out.printf("%-20s %-15s %-20s%n", "Ticket ID", "Vehicle", "Entry Time");
            System.out.println("-".repeat(60));
            for (ParkingTicket ticket : activeTickets.values()) {
                System.out.printf("%-20s %-15s %-20s%n",
                        ticket.getTicketId(),
                        ticket.getVehicleLicensePlate(),
                        ticket.getFormattedEntryTime());
            }
        }
        System.out.println("=".repeat(60) + "\n");
    }

    @Override
    public String toString() {
        return "TicketService{" +
                "activeTickets=" + activeTickets.size() +
                ", totalTickets=" + totalTicketsIssued +
                ", totalRevenue=$" + getTotalRevenue() +
                '}';
    }
}
