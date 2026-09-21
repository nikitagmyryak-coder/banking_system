package domain_models;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Base account class: holds account number, holder name, password;
 * knows how to validate and perform deposit/withdraw.
 * writes transaction history
 */

public abstract class Account implements AutoCloseable {
    private final String accountNumber;
    private final String holderName;
    private final String password;
    protected double balance = 0.0;
    protected List<Transaction> transactions = new ArrayList<>();
    private static int nextTransactionId = 1;

    public Account(String accountNumber, String holderName, String password) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.password = password;

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

    public String getPassword(){
        return password;
    }

    public List<Transaction> getTransactionHistory(){
        return new ArrayList<>(transactions);
    }

    // =============== METHODS ===============
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            writeTransaction("Deposit", amount);
        }
        else {
            throw new InvalidAmountException("Invalid amount. Cannot deposit negative balance");
        }
    }

    public void withdraw(double amount) {
        if(amount <= 0) {
            throw new InvalidAmountException("Invalid amount. Cannot withdraw negative balance");
        }
        if (balance >= amount) {
            balance -= amount;
            writeTransaction("Withdraw", amount);
        }
        else {
            throw new InsufficientFundsException("Insufficient Funds");
        }
    }

    protected void writeTransaction(String type, double amount){
        String transactionId = String.format("TXN-%04d", nextTransactionId);
        nextTransactionId++;
        LocalDateTime time = LocalDateTime.now();
        transactions.add(new Transaction(transactionId, type, amount, time));
    }

    protected void setBalance(double balance){
        this.balance = balance;
    }

    @Override
    public String toString(){
        return "[" + accountNumber + "] " + "Holder: " + holderName + " | Current balance: " + String.format("%.2f",balance) + "$";
    }
}
