package domain_models;

import exceptions.AccountNotFoundException;
import exceptions.InvalidAmountException;

import java.util.List;

/**
 * business logic for account operations (deposit, withdraw, transfer); works through AccountRepository
 */

public class AccountService {
    private final AccountRepository repository;
    private final JdbcTransactionRepository transactionRepository;
    private int nextAccountNumber = 1;

    public AccountService(AccountRepository repository, JdbcTransactionRepository transactionRepository) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
    }

    public Account getAccount(String accountNumber){
        return repository
                .findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account was not found"));
    }
    public void deposit(String accountNumber, double amount){
        Account temp = getAccount(accountNumber);
        temp.deposit(amount);
        repository.save(temp);

        List<Transaction> transactionsList = temp.getTransactionHistory();
        Transaction last = transactionsList.get(transactionsList.size() - 1);
        transactionRepository.save(temp, last);
    }

    public void withdraw(String accountNumber, double amount){
        Account temp = getAccount(accountNumber);
        temp.withdraw(amount);
        repository.save(temp);

        List<Transaction> transactionsList = temp.getTransactionHistory();
        Transaction last = transactionsList.get(transactionsList.size() - 1);
        transactionRepository.save(temp, last);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount){
        withdraw(fromAccountNumber, amount);
        deposit(toAccountNumber, amount);
    }

    private String generateAccountHelper() {
        String number = String.format("ACC-%04d", nextAccountNumber);
        nextAccountNumber += 1;
        return number;
    }

    public CheckingAccount createCheckingAccount(String holderName, double initialBalance, double overdraftLimit){
        CheckingAccount temp = new CheckingAccount(
                generateAccountHelper(), holderName, initialBalance, overdraftLimit);
        repository.save(temp);
        return temp;
    }
    public SavingsAccount createSavingsAccount(String holderName, double initialBalance, double interestRate){
        SavingsAccount temp = new SavingsAccount(
                generateAccountHelper(), holderName, initialBalance, interestRate);
        repository.save(temp);
        return temp;
    }

    public List<Account> getAllAccounts(){
        return repository.findAll();
    }

    public void addInterestRate(String accountNumber){
        Account temp = getAccount(accountNumber);

        if(temp instanceof SavingsAccount){
            SavingsAccount savings = (SavingsAccount) temp;
            savings.addInterest();
            repository.save(temp);
        } else{
            throw new UnsupportedOperationException("not a saving account");
        }
    }
}