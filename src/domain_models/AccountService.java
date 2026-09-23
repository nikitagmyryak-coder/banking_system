package domain_models;

import exceptions.AccountNotFoundException;
import exceptions.InvalidAmountException;

import java.sql.*;
import java.util.List;

/**
 * business logic for account operations (deposit, withdraw, transfer); works through AccountRepository
 */

public class AccountService {
    private final AccountRepository repository;
    private final JdbcTransactionRepository transactionRepository;
    private int nextAccountNumber;
    private int nextTransactionId;


    public AccountService(AccountRepository repository, JdbcTransactionRepository transactionRepository) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
        this.nextAccountNumber = repository.count() + 1;
        this.nextTransactionId = transactionRepository.count() + 1;
    }


    public Account getAccount(String accountNumber){
        return repository
                .findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account was not found"));
    }
    public void deposit(String accountNumber, double amount){
        Account temp = getAccount(accountNumber);
        temp.deposit(generateTransactionId(), amount);
        repository.save(temp);

        List<Transaction> transactionsList = temp.getTransactionHistory();
        Transaction last = transactionsList.get(transactionsList.size() - 1);
        transactionRepository.save(temp, last);
    }

    public void withdraw(String accountNumber, double amount){
        Account temp = getAccount(accountNumber);
        temp.withdraw(generateTransactionId(), amount);
        repository.save(temp);

        List<Transaction> transactionsList = temp.getTransactionHistory();
        Transaction last = transactionsList.get(transactionsList.size() - 1);
        transactionRepository.save(temp, last);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount){

        if(fromAccountNumber.equals(toAccountNumber)){
            System.out.println("User tried to transfer to themself");
            throw new InvalidAmountException("Cannot transfer to the same account");
        }

        withdraw(fromAccountNumber, amount);
        deposit(toAccountNumber, amount);
    }

    private String generateAccountHelper() {
        String number = String.format("ACC-%04d", nextAccountNumber);
        nextAccountNumber += 1;
        return number;
    }

    private String generateTransactionId(){
        String id = String.format("TXN-%04d", nextTransactionId);
        nextTransactionId += 1;
        return id;
    }

    public CheckingAccount createCheckingAccount(String holderName, String password, double overdraftLimit){
        CheckingAccount temp = new CheckingAccount(
                generateAccountHelper(), holderName, password, overdraftLimit);
        repository.save(temp);
        return temp;
    }
    public SavingsAccount createSavingsAccount(String holderName, String password, double interestRate){
        SavingsAccount temp = new SavingsAccount(
                generateAccountHelper(), holderName, password, interestRate);
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

    public List<Transaction> getTransactionHistory(String accountNumber){
        return transactionRepository.findByAccountNumber(accountNumber);
    }
}