package Tickets_Payments;

import Accounts_Vehicles.Vehicle;
import Accounts_Vehicles.enums.TicketStatus;
import Infrastructure.ParkingSpot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ParkingTicket {
    private String ticketId;
    private String vehicleLicensePlate;
    private Vehicle vehicle;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private LocalDateTime paymentTime;
    private String entryPanelId;
    private String exitPanelId;
    private TicketStatus status;
    private double amountPaid;
    private ParkingSpot assignedSpot;

    public ParkingTicket(String ticketId, Vehicle vehicle, String entryPanelId) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (entryPanelId == null || entryPanelId.trim().isEmpty()) {
            throw new IllegalArgumentException("Entry panel ID cannot be null or empty");
        }

        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.vehicleLicensePlate = vehicle.getLicensePlate();
        this.entryTime = LocalDateTime.now();
        this.entryPanelId = entryPanelId;
        this.status = TicketStatus.ACTIVE;
        this.amountPaid = 0.0;
        this.exitTime = null;
        this.paymentTime = null;
        this.exitPanelId = null;
        this.assignedSpot = null;
    }

    public ParkingTicket(Vehicle vehicle, String entryPanelId) {
        this(generateTicketId(), vehicle, entryPanelId);
    }

    private static String generateTicketId() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uniqueId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "TKT_" + timestamp + "_" + uniqueId;
    }

    public double getDurationHours() {
        LocalDateTime endTime = (exitTime != null) ? exitTime : LocalDateTime.now();
        long minutes = java.time.Duration.between(entryTime, endTime).toMinutes();
        double hours = minutes / 60.0;
        return Math.max(0.5, Math.ceil(hours * 2) / 2);
    }

    public long getDurationMinutes() {
        LocalDateTime endTime = (exitTime != null) ? exitTime : LocalDateTime.now();
        return java.time.Duration.between(entryTime, endTime).toMinutes();
    }

    public void markAsPaid() {
        if (status == TicketStatus.PAID) {
            throw new IllegalStateException("Ticket is already paid");
        }
        this.status = TicketStatus.PAID;
        this.paymentTime = LocalDateTime.now();
        System.out.println("Ticket " + ticketId + " marked as PAID at " + paymentTime);
    }

    public void markAsPaid(double amount) {
        markAsPaid();
        this.amountPaid = amount;
    }

    public void markAsLost() {
        this.status = TicketStatus.LOST;
        System.out.println("Ticket " + ticketId + " marked as LOST");
    }

    public void markExited(String exitPanelId) {
        if (status != TicketStatus.PAID) {
            throw new IllegalStateException("Cannot exit. Ticket is not paid. Status: " + status);
        }
        this.exitTime = LocalDateTime.now();
        this.exitPanelId = exitPanelId;
        System.out.println("Ticket " + ticketId + " exited at " + exitTime);
    }

    public boolean isPaid() {
        return status == TicketStatus.PAID;
    }

    public boolean isActive() {
        return status == TicketStatus.ACTIVE;
    }

    public boolean isLost() {
        return status == TicketStatus.LOST;
    }

    public boolean isExpired() {
        if (!isActive()) return false;
        long hours = getDurationMinutes() / 60;
        return hours > 24;
    }

    public double calculateLateFee() {
        if (!isExpired()) return 0.0;
        long hoursOver = (getDurationMinutes() / 60) - 24;
        return hoursOver * 10.0;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public String getEntryPanelId() {
        return entryPanelId;
    }

    public String getExitPanelId() {
        return exitPanelId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public ParkingSpot getAssignedSpot() {
        return assignedSpot;
    }

    public void setAssignedSpot(ParkingSpot assignedSpot) {
        this.assignedSpot = assignedSpot;
    }

    public void setExitPanelId(String exitPanelId) {
        this.exitPanelId = exitPanelId;
    }

    public String getFormattedEntryTime() {
        return entryTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getFormattedExitTime() {
        return exitTime != null ? exitTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A";
    }

    @Override
    public String toString() {
        return "ParkingTicket{" +
                "ticketId='" + ticketId + '\'' +
                ", vehicleLicensePlate='" + vehicleLicensePlate + '\'' +
                ", entryTime=" + getFormattedEntryTime() +
                ", status=" + status +
                ", amountPaid=" + amountPaid +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ParkingTicket that = (ParkingTicket) obj;
        return ticketId.equals(that.ticketId);
    }

    @Override
    public int hashCode() {
        return ticketId.hashCode();
    }
}
