package domain_models;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

/**
 * an account with an overdraft limit, overrides withdraw to allow going negative up to that limit
*/
public class CheckingAccount extends Account {

    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String holderName, double balance, double overdraftLimit) {
        super(accountNumber, holderName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Invalid amount. Must be positive");
        }
        if ((balance - amount) >= -overdraftLimit) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Exceeds overdraft limit");
        }
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}
