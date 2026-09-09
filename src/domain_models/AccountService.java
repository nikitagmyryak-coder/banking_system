package domain_models;

import exceptions.AccountNotFoundException;

public class AccountService {
    private final AccountRepository repository;

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
}