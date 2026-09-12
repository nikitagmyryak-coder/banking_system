import domain_models.*;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        JdbcAccountRepository repo = new JdbcAccountRepository();
        Optional<Account> found = repo.findById("ACC-TEST-01");
        System.out.println(found.get());

//        JdbcAccountRepository repo = new JdbcAccountRepository();
//        CheckingAccount test = new CheckingAccount("ACC-TEST-01", "Test Holder", 500, 1000);
//        repo.save(test);

//        String url = "jdbc:mysql://localhost:3306/banking_system";
//        String user = "root";
//        String password = "My_Mysql1";
//
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(url, user, password);
//        } catch(SQLException e){
//            System.out.println("Caught: " + e.getMessage());
//        }
//        System.out.println("Connected: " + connection);

//
//        InMemoryAccountRepository imar = new InMemoryAccountRepository();
//        AccountService as = new AccountService(imar);
//
//        CheckingAccount c1 = as.createCheckingAccount("C One", 67, 2000);
//        CheckingAccount c2 = as.createCheckingAccount("C Two", 605, 100);
//        CheckingAccount c3 = as.createCheckingAccount("C Three", 4445, 5000);
//
//        SavingsAccount s1 = as.createSavingsAccount("S Odin", 228, 0.05);
//        SavingsAccount s2 = as.createSavingsAccount("S Dwa", 959595, 0.1);
//        SavingsAccount s3 = as.createSavingsAccount("S Tri", 5678, 0.07);
//
//        as.addInterestRate(s2.getAccountNumber());
//        as.transfer(c3.getAccountNumber(), s3.getAccountNumber(), 100);
//        as.transfer(c2.getAccountNumber(), s1.getAccountNumber(), 5);
//        as.deposit(c1.getAccountNumber(), 50);
//        as.withdraw(c1.getAccountNumber(), 20);
//
//        try{
//            as.withdraw(c1.getAccountNumber(), 3000);
//        } catch(InsufficientFundsException ife){
//            System.err.println("Caught: " + ife.getMessage());
//        }
//
//        for (Account acc : as.getAllAccounts()) {
//            System.out.println(acc);
//        }
//
//        for (Transaction t : c1.getTransactionHistory()) {
//            System.out.println(t);
//        }


    }
}