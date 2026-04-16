package Tickets_Payments;

import Accounts_Vehicles.enums.PaymentMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Payment {
    private String paymentId;
    private String ticketId;
    private double amount;
    private PaymentMethod method;
    private LocalDateTime paymentTime;
    private String cardLastFour;
    private boolean isSuccessful;
    private String transactionReference;

    public Payment(String ticketId, double amount, PaymentMethod method) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (method == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }

        this.paymentId = generatePaymentId();
        this.ticketId = ticketId;
        this.amount = amount;
        this.method = method;
        this.paymentTime = LocalDateTime.now();
        this.isSuccessful = false;
        this.transactionReference = generateTransactionReference();
        this.cardLastFour = null;
    }

    private String generatePaymentId() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uniqueId = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "PAY_" + timestamp + "_" + uniqueId;
    }

    private String generateTransactionReference() {
        return "TXN_" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }

    public boolean process() {
        System.out.println("\n" + "-".repeat(40));
        System.out.println("Processing Payment...");
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Amount: $" + String.format("%.2f", amount));
        System.out.println("Method: " + method.getValue());
        System.out.println("-".repeat(40));

        // Simulate payment processing
        isSuccessful = simulatePaymentProcessing();

        if (isSuccessful) {
            System.out.println("✓ Payment successful!");
            System.out.println("Transaction Reference: " + transactionReference);
        } else {
            System.out.println("✗ Payment failed. Please try again.");
        }

        System.out.println("-".repeat(40) + "\n");
        return isSuccessful;
    }

    private boolean simulatePaymentProcessing() {
        // Simulate network delay and success rate (95% success)
        try {
            Thread.sleep(500); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 95% success rate for simulation
        return Math.random() < 0.95;
    }

    public void setCardDetails(String cardNumber) {
        if (cardNumber != null && cardNumber.length() >= 4) {
            this.cardLastFour = cardNumber.substring(cardNumber.length() - 4);
        }
    }

    public void refund() {
        if (!isSuccessful) {
            throw new IllegalStateException("Cannot refund a failed payment");
        }
        System.out.println("Processing refund of $" + String.format("%.2f", amount) + " for payment " + paymentId);
        this.isSuccessful = false;
        System.out.println("Refund completed successfully.");
    }

    public void printReceipt() {
        if (!isSuccessful) {
            System.out.println("Cannot print receipt for failed payment.");
            return;
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("              PAYMENT RECEIPT");
        System.out.println("=".repeat(50));
        System.out.println("Payment ID:           " + paymentId);
        System.out.println("Transaction Ref:      " + transactionReference);
        System.out.println("Ticket ID:            " + ticketId);
        System.out.println("Amount:               $" + String.format("%.2f", amount));
        System.out.println("Payment Method:       " + method.getValue());
        if (cardLastFour != null) {
            System.out.println("Card Last Four:       ****" + cardLastFour);
        }
        System.out.println("Payment Time:         " + paymentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("Status:               SUCCESSFUL");
        System.out.println("=".repeat(50));
        System.out.println("  Thank you for your payment!");
        System.out.println("=".repeat(50) + "\n");
    }

    // Getters
    public String getPaymentId() {
        return paymentId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public String getCardLastFour() {
        return cardLastFour;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", amount=" + amount +
                ", method=" + method +
                ", successful=" + isSuccessful +
                '}';
    }
}
