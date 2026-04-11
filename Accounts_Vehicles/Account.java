package Accounts_Vehicles;

import Accounts_Vehicles.enums.AccountType;

public abstract class Account {
    protected String accountId;
    protected String name;
    protected String email;
    protected String password;
    protected AccountType accountType;
    protected boolean isLoggedIn;

    public Account(String accountId, String name, String email, String password, AccountType accountType) {
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }
        if (accountType == null) {
            throw new IllegalArgumentException("Account type cannot be null");
        }

        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.isLoggedIn = false;
    }

    public boolean login(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        if (this.password.equals(password)) {
            this.isLoggedIn = true;
            System.out.println(name + " logged in successfully");
            return true;
        }
        System.out.println("Invalid password for user: " + name);
        return false;
    }

    public void logout() {
        if (!isLoggedIn) {
            throw new IllegalStateException("User is not logged in");
        }
        this.isLoggedIn = false;
        System.out.println(name + " logged out");
    }

    // Getters
    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!this.password.equals(oldPassword)) {
            throw new SecurityException("Old password is incorrect");
        }
        if (newPassword == null || newPassword.length() < 4) {
            throw new IllegalArgumentException("New password must be at least 4 characters");
        }
        this.password = newPassword;
        System.out.println("Password changed successfully for " + name);
    }

    public abstract String getRoleDescription();

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", accountType=" + accountType +
                '}';
    }
}
