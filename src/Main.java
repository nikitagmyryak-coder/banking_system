import domain_models.*;
import gui.LoginPage;

public class Main {
    public static void main(String[] args) {

        JdbcAccountRepository jar = new JdbcAccountRepository();
        JdbcTransactionRepository jtr = new JdbcTransactionRepository();
        AccountService as = new AccountService(jar,jtr);


        LoginPage login = new LoginPage(as);

    }
}