package domain_models;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

public abstract class Account {
    private final String accountNumber;
    private final String holderName;
    protected double balance;

    public Account(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    // =============== GETTERS ===============
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getHolderName() {
        return holderName;
    }
    public double getBalance() {
        return balance;
    }


    // =============== METHODS ===============
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
        else {
            throw new InvalidAmountException("Invalid amount. Cannot deposit negative balance");
        }
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Insufficient Funds");
        }
    }
}
