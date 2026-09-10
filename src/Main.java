import domain_models.AccountService;
import domain_models.CheckingAccount;
import domain_models.InMemoryAccountRepository;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;

public class Main {
    public static void main(String[] args) {
        InMemoryAccountRepository imar = new InMemoryAccountRepository();
        AccountService as = new AccountService(imar);

        CheckingAccount test = as.createCheckingAccount("Holder Name", 67, 2000);
        System.out.println("Before: " + test.getBalance() + " " + test.getAccountNumber());

        as.deposit(test.getAccountNumber(), 100);
        System.out.println("After deposit: " + test.getBalance() + " " + test.getAccountNumber());

        try{
            as.deposit("ACC-9999", 50);
        } catch(AccountNotFoundException anfe){
            System.out.println("Caught: " + anfe.getMessage());
        }

        try{
           as.withdraw(test.getAccountNumber(), 69000);
        } catch(InsufficientFundsException ife){
            System.out.println("Caught: " + ife.getMessage());
        }

        System.out.println(test.getTransactionHistory());
    }
}