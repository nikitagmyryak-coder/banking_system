package domain_models;

/**
 * a savings account with an interest rate and interest accrual
 */
public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(String accountNumber, String holderName, String password, double interestRate) {
        super(accountNumber, holderName, password);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void addInterest(){
        double interest = interestRate * balance;
        balance += interest;
    }

    @Override
    public String toString(){
        return "Savings Account: " + super.toString() + " | interest rate: " + String.format("%.1f%%", interestRate * 100);
    }

    @Override
    public void close() throws Exception {

    }
}
