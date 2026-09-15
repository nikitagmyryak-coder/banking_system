import domain_models.*;

public class Main {
    public static void main(String[] args) {

        JdbcAccountRepository jar = new JdbcAccountRepository();
        AccountService as = new AccountService(jar);

        CheckingAccount c1 = as.createCheckingAccount("Checking One", 500, 1000);
        CheckingAccount c2 = as.createCheckingAccount("Checking Two", 800, 500);
        CheckingAccount c3 = as.createCheckingAccount("Checking Three", 300, 2000);

        SavingsAccount s1 = as.createSavingsAccount("Savings One", 1000, 0.05);
        SavingsAccount s2 = as.createSavingsAccount("Savings Two", 2000, 0.03);
        SavingsAccount s3 = as.createSavingsAccount("Savings Three", 500, 0.07);

        as.deposit(c1.getAccountNumber(), 100);
        as.withdraw(c2.getAccountNumber(), 50);
        as.transfer(c3.getAccountNumber(), s1.getAccountNumber(), 100);
        as.addInterestRate(s2.getAccountNumber());


    }
}