package domain_models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcAccountRepository implements AccountRepository{
    private final String url = "jdbc:mysql://localhost:3306/banking_system";
    private final String user = "root";
    private final String password = "My_Mysql1";

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO accounts (account_number, holder_name, balance, account_type) VALUES (?, ?, ?, ?)";
        String sqlChecking = "INSERT INTO checking_accounts (account_number, overdraft_limit) VALUES (?, ?)";
        String sqlSavings = "INSERT INTO savings_accounts (account_number, interest_rate) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, account.getAccountNumber());
            stmt.setString(2, account.getHolderName());
            stmt.setDouble(3, account.getBalance());
            stmt.setString(4, account instanceof CheckingAccount ? "CHECKING" : "SAVINGS");

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save account: " + e.getMessage(), e);
        }

        try(Connection connection = DriverManager.getConnection(url, user, password)){
            if(account instanceof CheckingAccount){
                CheckingAccount ca = (CheckingAccount) account;

                try (PreparedStatement stmt = connection.prepareStatement(sqlChecking)) {
                    stmt.setString(1, ca.getAccountNumber());
                    stmt.setDouble(2, ca.getOverdraftLimit());
                    stmt.executeUpdate();
                }
            } else if(account instanceof SavingsAccount){
                SavingsAccount sa = (SavingsAccount) account;

                try(PreparedStatement stmt = connection.prepareStatement(sqlSavings)){
                    stmt.setString(1, sa.getAccountNumber());
                    stmt.setDouble(2, sa.getInterestRate());
                    stmt.executeUpdate();
                }
            }
        } catch(SQLException e){
            throw new RuntimeException("Failed to save account: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Account> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Account> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public boolean existsById(String id) {
        return false;
    }


}
