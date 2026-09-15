import domain_models.*;

public class Main {
    public static void main(String[] args) {

        JdbcAccountRepository jar = new JdbcAccountRepository();
        JdbcTransactionRepository jtr = new JdbcTransactionRepository();
        AccountService as = new AccountService(jar,jtr);

        CheckingAccount c1 = as.createCheckingAccount("Checking One", 500, 1000);
        CheckingAccount c2 = as.createCheckingAccount("Checking Two", 800, 500);
        CheckingAccount c3 = as.createCheckingAccount("Checking Three", 300, 2000);

        SavingsAccount s1 = as.createSavingsAccount("Savings One", 1000, 0.05);
        SavingsAccount s2 = as.createSavingsAccount("Savings Two", 2000, 0.03);
        SavingsAccount s3 = as.createSavingsAccount("Savings Three", 500, 0.07);

        CheckingAccount c4 = as.createCheckingAccount("Checking Four", 1500, 500);
        SavingsAccount s4 = as.createSavingsAccount("Savings Four", 3000, 0.04);

        as.deposit(c1.getAccountNumber(), 100);
        as.withdraw(c2.getAccountNumber(), 50);
        as.transfer(c3.getAccountNumber(), s1.getAccountNumber(), 100);
        as.addInterestRate(s2.getAccountNumber());


        as.deposit(c4.getAccountNumber(), 250);
        as.withdraw(c4.getAccountNumber(), 1000);
        as.transfer(s4.getAccountNumber(), c4.getAccountNumber(), 500);
        as.addInterestRate(s4.getAccountNumber());

    }
}