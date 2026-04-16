package Tickets_Payments;

import Accounts_Vehicles.enums.PaymentMethod;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class PaymentService {
    private Map<String, Payment> paymentHistory;
    private Map<String, List<Payment>> ticketPayments;
    private double totalRevenue;
    private int totalTransactions;

    public PaymentService() {
        this.paymentHistory = new HashMap<>();
        this.ticketPayments = new HashMap<>();
        this.totalRevenue = 0.0;
        this.totalTransactions = 0;
    }

    public boolean processPayment(ParkingTicket ticket, double amount, PaymentMethod method) {
        if (ticket == null) {
            throw new IllegalArgumentException("ParkingTicket cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (method == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }

        if (ticket.isPaid()) {
            System.out.println("Ticket " + ticket.getTicketId() + " is already paid.");
            return true;
        }

        Payment payment = new Payment(ticket.getTicketId(), amount, method);

        // For credit card, we can add card details
        if (method == PaymentMethod.CREDIT_CARD) {
            payment.setCardDetails("4111111111111111"); // Mock card number
        }

        boolean success = payment.process();

        if (success) {
            paymentHistory.put(payment.getPaymentId(), payment);

            ticketPayments.computeIfAbsent(ticket.getTicketId(), k -> new ArrayList<>()).add(payment);

            ticket.markAsPaid(amount);
            totalRevenue += amount;
            totalTransactions++;

            payment.printReceipt();
        }

        return success;
    }

    public boolean processCashPayment(ParkingTicket ticket, double amount) {
        return processPayment(ticket, amount, PaymentMethod.CASH);
    }

    public boolean processCreditCardPayment(ParkingTicket ticket, double amount) {
        return processPayment(ticket, amount, PaymentMethod.CREDIT_CARD);
    }

    public boolean processCreditCardPayment(ParkingTicket ticket, double amount, String cardNumber, String expiry, String cvv) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be empty");
        }
        if (expiry == null || expiry.trim().isEmpty()) {
            throw new IllegalArgumentException("Expiry date cannot be empty");
        }
        if (cvv == null || cvv.trim().isEmpty()) {
            throw new IllegalArgumentException("CVV cannot be empty");
        }

        // Validate card (basic validation)
        if (!isValidCardNumber(cardNumber)) {
            System.out.println("Invalid card number.");
            return false;
        }

        if (!isValidExpiry(expiry)) {
            System.out.println("Invalid expiry date.");
            return false;
        }

        return processPayment(ticket, amount, PaymentMethod.CREDIT_CARD);
    }

    private boolean isValidCardNumber(String cardNumber) {
        // Simple validation: 16 digits
        return cardNumber != null && cardNumber.replaceAll("\\s", "").matches("\\d{16}");
    }

    private boolean isValidExpiry(String expiry) {
        // Simple validation: MM/YY format
        return expiry != null && expiry.matches("(0[1-9]|1[0-2])/(\\d{2})");
    }

    public Payment getPayment(String paymentId) {
        if (paymentId == null || paymentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment ID cannot be null or empty");
        }
        return paymentHistory.get(paymentId);
    }

    public List<Payment> getPaymentsForTicket(String ticketId) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty");
        }
        return ticketPayments.getOrDefault(ticketId, new ArrayList<>());
    }

    public boolean refundPayment(String paymentId) {
        Payment payment = getPayment(paymentId);
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found: " + paymentId);
        }

        if (!payment.isSuccessful()) {
            throw new IllegalStateException("Cannot refund a failed payment");
        }

        payment.refund();
        totalRevenue -= payment.getAmount();
        totalTransactions--;

        return true;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getTotalTransactions() {
        return totalTransactions;
    }

    public int getSuccessfulTransactionsCount() {
        int count = 0;
        for (Payment payment : paymentHistory.values()) {
            if (payment.isSuccessful()) {
                count++;
            }
        }
        return count;
    }

    public int getFailedTransactionsCount() {
        return totalTransactions - getSuccessfulTransactionsCount();
    }

    public double getRevenueByMethod(PaymentMethod method) {
        double revenue = 0.0;
        for (Payment payment : paymentHistory.values()) {
            if (payment.isSuccessful() && payment.getMethod() == method) {
                revenue += payment.getAmount();
            }
        }
        return revenue;
    }

    public void generateRevenueReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              REVENUE REPORT");
        System.out.println("=".repeat(60));
        System.out.println("Total Transactions:   " + totalTransactions);
        System.out.println("Successful:           " + getSuccessfulTransactionsCount());
        System.out.println("Failed:               " + getFailedTransactionsCount());
        System.out.println("-".repeat(60));
        System.out.printf("Cash Revenue:         $%.2f%n", getRevenueByMethod(PaymentMethod.CASH));
        System.out.printf("Credit Card Revenue:  $%.2f%n", getRevenueByMethod(PaymentMethod.CREDIT_CARD));
        System.out.println("-".repeat(60));
        System.out.printf("TOTAL REVENUE:        $%.2f%n", totalRevenue);
        System.out.println("=".repeat(60) + "\n");
    }

    public void displayPaymentHistory() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                 PAYMENT HISTORY");
        System.out.println("=".repeat(70));

        if (paymentHistory.isEmpty()) {
            System.out.println("No payments recorded.");
        } else {
            System.out.printf("%-20s %-15s %-10s %-12s %s%n",
                    "Payment ID", "Ticket ID", "Amount", "Method", "Status");
            System.out.println("-".repeat(70));

            for (Payment payment : paymentHistory.values()) {
                System.out.printf("%-20s %-15s $%-9.2f %-12s %s%n",
                        payment.getPaymentId(),
                        payment.getTicketId(),
                        payment.getAmount(),
                        payment.getMethod().getValue(),
                        payment.isSuccessful() ? "SUCCESS" : "FAILED");
            }
        }
        System.out.println("=".repeat(70) + "\n");
    }

    @Override
    public String toString() {
        return "PaymentService{" +
                "totalTransactions=" + totalTransactions +
                ", totalRevenue=$" + totalRevenue +
                '}';
    }
}