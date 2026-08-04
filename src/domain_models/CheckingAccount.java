package domain_models;

import exceptions.InsufficientFundsException;

public class CheckingAccount extends Account {

    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String holderName, double balance) {
        super(accountNumber, holderName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if((balance - amount) >= -overdraftLimit) {
            balance -= amount;
        }
        else {
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
