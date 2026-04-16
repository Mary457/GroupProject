package Tickets_Payments;

import java.util.HashMap;
import java.util.Map;

public class ParkingRate {
    private Map<Integer, Double> hourlyRates;
    private double defaultRate;
    private double minimumRate;
    private double maximumDailyRate;

    public ParkingRate() {
        this.hourlyRates = new HashMap<>();
        initializeDefaultRates();
        this.defaultRate = 2.5;
        this.minimumRate = 1.0;
        this.maximumDailyRate = 25.0;
    }

    private void initializeDefaultRates() {
        // First hour: $4.00
        hourlyRates.put(1, 4.0);
        // Second hour: $3.50
        hourlyRates.put(2, 3.5);
        // Third hour: $3.50
        hourlyRates.put(3, 3.5);
    }

    public double calculateCharge(double hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("Hours cannot be negative");
        }

        if (hours <= 0.5) {
            return minimumRate;
        }

        double total = 0.0;
        double remainingHours = hours;

        // Apply hourly rates for first 3 hours
        for (int hour = 1; hour <= 3 && remainingHours > 0; hour++) {
            double rate = hourlyRates.getOrDefault(hour, defaultRate);
            total += rate;
            remainingHours--;
        }

        // Apply default rate for remaining hours
        if (remainingHours > 0) {
            total += remainingHours * defaultRate;
        }

        // Cap at maximum daily rate
        total = Math.min(total, maximumDailyRate);

        // Round to 2 decimal places
        return Math.round(total * 100.0) / 100.0;
    }

    public double calculateCharge(long minutes) {
        return calculateCharge(minutes / 60.0);
    }

    public double calculateCharge(ParkingTicket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("ParkingTicket cannot be null");
        }
        double hours = ticket.getDurationHours();
        double charge = calculateCharge(hours);

        // Add late fee if ticket is expired
        if (ticket.isExpired()) {
            double lateFee = ticket.calculateLateFee();
            charge += lateFee;
            System.out.println("Late fee of $" + lateFee + " applied for expired ticket");
        }

        return charge;
    }

    public void updateRate(int hour, double rate) {
        if (hour < 1 || hour > 24) {
            throw new IllegalArgumentException("Hour must be between 1 and 24");
        }
        if (rate < 0) {
            throw new IllegalArgumentException("Rate cannot be negative");
        }
        hourlyRates.put(hour, rate);
        System.out.println("Updated hour " + hour + " rate to $" + rate);
    }

    public void updateDefaultRate(double rate) {
        if (rate < 0) {
            throw new IllegalArgumentException("Rate cannot be negative");
        }
        this.defaultRate = rate;
        System.out.println("Updated default rate to $" + rate);
    }

    public void updateMinimumRate(double rate) {
        if (rate < 0) {
            throw new IllegalArgumentException("Rate cannot be negative");
        }
        this.minimumRate = rate;
        System.out.println("Updated minimum rate to $" + rate);
    }

    public void updateMaximumDailyRate(double rate) {
        if (rate < 0) {
            throw new IllegalArgumentException("Rate cannot be negative");
        }
        this.maximumDailyRate = rate;
        System.out.println("Updated maximum daily rate to $" + rate);
    }

    public double getRateForHour(int hour) {
        return hourlyRates.getOrDefault(hour, defaultRate);
    }

    public double getDefaultRate() {
        return defaultRate;
    }

    public double getMinimumRate() {
        return minimumRate;
    }

    public double getMaximumDailyRate() {
        return maximumDailyRate;
    }

    public Map<Integer, Double> getAllRates() {
        return new HashMap<>(hourlyRates);
    }

    public void displayRateCard() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         PARKING RATE CARD");
        System.out.println("=".repeat(50));
        System.out.printf("%-15s | %s%n", "HOURS", "RATE");
        System.out.println("-".repeat(50));

        for (int hour = 1; hour <= 3; hour++) {
            double rate = hourlyRates.getOrDefault(hour, defaultRate);
            if (hour == 1) {
                System.out.printf("First %-10d | $%.2f%n", hour, rate);
            } else {
                System.out.printf("Hour %-11d | $%.2f%n", hour, rate);
            }
        }

        System.out.printf("Each additional hour | $%.2f%n", defaultRate);
        System.out.println("-".repeat(50));
        System.out.printf("Minimum charge (30 min) | $%.2f%n", minimumRate);
        System.out.printf("Maximum daily rate | $%.2f%n", maximumDailyRate);
        System.out.println("=".repeat(50) + "\n");
    }

    @Override
    public String toString() {
        return "ParkingRate{" +
                "defaultRate=" + defaultRate +
                ", minimumRate=" + minimumRate +
                ", maximumDailyRate=" + maximumDailyRate +
                ", hourlyRates=" + hourlyRates +
                '}';
    }
}