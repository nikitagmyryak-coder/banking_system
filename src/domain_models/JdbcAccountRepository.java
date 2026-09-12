package domain_models;

import java.sql.*;
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
        String sqlSelect = "SELECT * FROM accounts WHERE account_number = ?";

        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(sqlSelect)){

            stmt.setString(1, id);
            ResultSet result = stmt.executeQuery();

            if(result.next()){
                String holderName = result.getString("holder_name");
                double balance = result.getDouble("balance");
                String accountType = result.getString("account_type");

                if(accountType.equals("CHECKING")){
                    String sqlChecking = "SELECT overdraft_limit FROM checking_accounts WHERE account_number = ?";

                    try(PreparedStatement stmt2 = connection.prepareStatement(sqlChecking)){

                        stmt2.setString(1, id);
                        ResultSet result2 = stmt2.executeQuery();

                        if(result2.next()){
                            double overdraftLimit = result2.getDouble("overdraft_limit");
                            return Optional.of(new CheckingAccount(id, holderName, balance, overdraftLimit));
                        }
                    }catch(SQLException e){
                        throw new RuntimeException("Failed to find account: " + e.getMessage(), e);
                    }

                }else{
                    String sqlSavings = "SELECT interest_rate FROM savings_accounts WHERE account_number = ?";

                    try(PreparedStatement stmt3 = connection.prepareStatement(sqlSavings)) {

                        stmt3.setString(1,id);
                        ResultSet result3 = stmt3.executeQuery();

                        if(result3.next()){
                            double interestRate = result3.getDouble("interest_rate");
                            return Optional.of(new SavingsAccount(id, holderName, balance, interestRate));
                        }
                    } catch(SQLException e){
                        throw new RuntimeException("Failed to find account: " + e.getMessage(), e);
                    }
                }


            }else{
                return Optional.empty();
            }

        } catch(SQLException e){
            throw new RuntimeException("Failed to find account: " + e.getMessage(), e);
        }
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
