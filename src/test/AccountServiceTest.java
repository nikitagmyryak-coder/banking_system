package test;

import domain_models.CheckingAccount;
import domain_models.AccountService;
import domain_models.InMemoryAccountRepository;
import domain_models.SavingsAccount;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountServiceTest {

    private InMemoryAccountRepository imr;
    private AccountService as;
    private CheckingAccount caTest;
    private SavingsAccount saTest;

    @BeforeEach
    public void setUp() {
        imr = new InMemoryAccountRepository();
        as = new AccountService(imr);
        caTest = as.createCheckingAccount("Checking Account Test", 500, 1000);
        saTest = as.createSavingsAccount("Savings Account", 10, 0.05);
    }

    @Test
    public void depositIncreasesBalanceSuccess() {
        caTest.deposit(200);
        assertEquals(700.0, caTest.getBalance(), 0.001);
    }

    @Test
    public void withdrawDecreasesBalanceSuccess() {
        caTest.withdraw(200);
        assertEquals(300.0, caTest.getBalance(), 0.001);
    }

    @Test
    public void withdrawFail() {
        assertThrows(InsufficientFundsException.class, () -> caTest.withdraw(2000));
    }

    @Test
    public void transferNegativeAmount() {
        assertThrows(InvalidAmountException.class, () -> as.transfer(
                caTest.getAccountNumber(), saTest.getAccountNumber(), -1.0));
    }

    @Test
    public void depositNegative(){
        assertThrows(InvalidAmountException.class, () -> as.deposit(caTest.getAccountNumber(), -1));
    }

    @Test
    public void transferHappy(){
        as.transfer(caTest.getAccountNumber(), saTest.getAccountNumber(), 1);
        assertEquals(11.0, saTest.getBalance(), 0.001);
    }

    @Test
    public void getAccountFail(){
        assertThrows(AccountNotFoundException.class,
                () -> as.getAccount(""));
    }

    @Test
    public void addInterestRateHappy(){
        as.addInterestRate(saTest.getAccountNumber());
        assertEquals(10.5, saTest.getBalance(), 0.001);
    }

    @Test
    public void addInterestRateFail(){
        assertThrows(UnsupportedOperationException.class,
                () -> as.addInterestRate(caTest.getAccountNumber()));
    }
}