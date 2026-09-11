package test;

import domain_models.CheckingAccount;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import domain_models.AccountService;
import domain_models.InMemoryAccountRepository;
import exceptions.InsufficientFundsException;
import org.junit.jupiter.api.Test;

public class AccountServiceTest {
    @org.junit.Test
    @Test
    public void depositIncreasesBalanceSuccess() {
        InMemoryAccountRepository imr = new InMemoryAccountRepository();
        AccountService as = new AccountService(imr);

        CheckingAccount caTest = as.createCheckingAccount("Checking Account Test", 500, 1000);

        caTest.deposit(200);
        assertEquals(700.0, caTest.getBalance(), 0.001);
    }

    @org.junit.Test
    @Test
    public void withdrawDecreasesBalanceSuccess(){
        InMemoryAccountRepository imr = new InMemoryAccountRepository();
        AccountService as = new AccountService(imr);

        CheckingAccount caTest = as.createCheckingAccount("Checking Account Test", 500, 1000);

        caTest.withdraw(200);
        assertEquals(300.0, caTest.getBalance(), 0.001);
    }

    @org.junit.Test
    @Test
    public void withdrawFail(){
        InMemoryAccountRepository imr = new InMemoryAccountRepository();
        AccountService as = new AccountService(imr);

        CheckingAccount caTest = as.createCheckingAccount("Checking Account Test", 500, 1000);

        assertThrows(InsufficientFundsException.class, () -> caTest.withdraw(2000));
    }


}
