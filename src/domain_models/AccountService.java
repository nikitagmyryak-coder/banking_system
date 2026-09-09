package domain_models;

import exceptions.AccountNotFoundException;

/**
 * business logic for account operations (deposit, withdraw, transfer); works through AccountRepository
 */

public class AccountService {
    private final AccountRepository repository;
    private int nextAccountNumber = 1;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account getAccount(String accountNumber){
        return repository
                .findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account was not found"));
    }
    public void deposit(String accountNumber, double amount){
        Account temp = getAccount(accountNumber);
        temp.deposit(amount);
    }

    public void withdraw(String accountNumber, double amount){
        Account temp = getAccount(accountNumber);
        temp.withdraw(amount);
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
}