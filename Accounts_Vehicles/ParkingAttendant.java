package Accounts_Vehicles;

import Accounts_Vehicles.enums.AccountType;
import Accounts_Vehicles.enums.TicketStatus;
import Panels_Portals.ExitPanel;
import Tickets_Payments.ParkingTicket;
import Tickets_Payments.PaymentService;

public class ParkingAttendant extends Account {

    public ParkingAttendant(String accountId, String name, String email, String password) {
        super(accountId, name, email, password, AccountType.PARKING_ATTENDANT);
    }

    @Override
    public String getRoleDescription() {
        return "Parking Attendant - Can process payments and assist customers";
    }

    public ParkingTicket scanTicket(ParkingTicket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("ParkingTicket cannot be null");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Attendant must be logged in to perform this action");
        }
        System.out.println("Attendant " + name + " scanned ticket " + ticket.getTicketId());
        return ticket;
    }

    public boolean processCashPayment(ParkingTicket ticket, double amount, PaymentService paymentService) {
        if (ticket == null) {
            throw new IllegalArgumentException("ParkingTicket cannot be null");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (paymentService == null) {
            throw new IllegalArgumentException("PaymentService cannot be null");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Attendant must be logged in to perform this action");
        }
        System.out.println("Attendant " + name + " processing cash payment of $" + amount);
        return paymentService.processCashPayment(ticket, amount);
    }

    public boolean assistCustomerExit(ExitPanel exitPanel, ParkingTicket ticket) {
        if (exitPanel == null) {
            throw new IllegalArgumentException("ExitPanel cannot be null");
        }
        if (ticket == null) {
            throw new IllegalArgumentException("ParkingTicket cannot be null");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Attendant must be logged in to perform this action");
        }
        System.out.println("Attendant " + name + " assisting customer at exit");
        return exitPanel.processPayment(ticket);
    }

    public void validateTicket(ParkingTicket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("ParkingTicket cannot be null");
        }
        if (!isLoggedIn) {
            throw new IllegalStateException("Attendant must be logged in to perform this action");
        }

        if (ticket.getStatus() == TicketStatus.PAID) {
            System.out.println("Ticket " + ticket.getTicketId() + " is already paid");
        } else if (ticket.getStatus() == TicketStatus.ACTIVE) {
            System.out.println("Ticket " + ticket.getTicketId() + " is active. Payment required.");
        } else {
            System.out.println("Ticket " + ticket.getTicketId() + " is invalid (Status: " + ticket.getStatus() + ")");
        }
    }
}
