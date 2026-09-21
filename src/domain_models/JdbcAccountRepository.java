package domain_models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcAccountRepository implements AccountRepository{
    private final String url = "jdbc:mysql://localhost:3306/banking_system";
    private final String user = "root";
    private final String passwordSQL = "My_Mysql1";
    private String index = "SELECT COUNT(*) FROM accounts";

    @Override
    public int count(){
        try (Connection connection = DriverManager.getConnection(url, user, passwordSQL);
             PreparedStatement stmt = connection.prepareStatement(index);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred: " + e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO accounts (account_number, holder_name, balance, account_type, password) VALUES (?, ?, ?, ?, ?)";
        String sqlChecking = "INSERT INTO checking_accounts (account_number, overdraft_limit) VALUES (?, ?)";
        String sqlSavings = "INSERT INTO savings_accounts (account_number, interest_rate) VALUES (?, ?)";
        String sqlUpdate = "UPDATE accounts SET holder_name = ?, balance = ?, account_type = ? WHERE account_number = ?";
        boolean exists = existsById(account.getAccountNumber());


        try(Connection connection = DriverManager.getConnection(url, user, passwordSQL)){

            if(!exists) {
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                    stmt.setString(1, account.getAccountNumber());
                    stmt.setString(2, account.getHolderName());
                    stmt.setDouble(3, 0.0);
                    stmt.setString(4, account instanceof CheckingAccount ? "CHECKING" : "SAVINGS");
                    stmt.setString(5, account.getPassword());

                    stmt.executeUpdate();

                    if (account instanceof CheckingAccount) {
                        CheckingAccount ca = (CheckingAccount) account;

                        try (PreparedStatement stmtC = connection.prepareStatement(sqlChecking)) {
                            stmtC.setString(1, ca.getAccountNumber());
                            stmtC.setDouble(2, ca.getOverdraftLimit());
                            stmtC.executeUpdate();
                        }
                    } else if(account instanceof SavingsAccount){
                        SavingsAccount sa = (SavingsAccount) account;

                        try(PreparedStatement stmtS = connection.prepareStatement(sqlSavings)){
                            stmtS.setString(1, sa.getAccountNumber());
                            stmtS.setDouble(2, sa.getInterestRate());
                            stmtS.executeUpdate();
                        }

                    }



                }

            }else{
                //"UPDATE accounts SET holder_name = ?, balance = ?, account_type = ? WHERE account_number = ?"
                try(PreparedStatement stmtU = connection.prepareStatement(sqlUpdate)){
                    stmtU.setString(1,account.getHolderName());
                    stmtU.setDouble(2, account.getBalance());
                    stmtU.setString(3, account instanceof CheckingAccount ? "CHECKING" : "SAVINGS");
                    stmtU.setString(4, account.getAccountNumber());

                    stmtU.executeUpdate();
                }
            }


        } catch(SQLException e){
            throw new RuntimeException("An error occurred: " + e.getMessage(), e);
        }


    }

    @Override
    public Optional<Account> findById(String id) {
        String sqlSelect = "SELECT * FROM accounts WHERE account_number = ?";

        try(Connection connection = DriverManager.getConnection(url, user, passwordSQL);
            PreparedStatement stmt = connection.prepareStatement(sqlSelect)){

            stmt.setString(1, id);
            ResultSet result = stmt.executeQuery();

            if(result.next()){
                String holderName = result.getString("holder_name");
                String password = result.getString("password");
                String accountType = result.getString("account_type");
                double balance = result.getDouble("balance");

                if(accountType.equals("CHECKING")){
                    String sqlChecking = "SELECT overdraft_limit FROM checking_accounts WHERE account_number = ?";

                    try(PreparedStatement stmt2 = connection.prepareStatement(sqlChecking)){

                        stmt2.setString(1, id);
                        ResultSet result2 = stmt2.executeQuery();

                        if(result2.next()){
                            double overdraftLimit = result2.getDouble("overdraft_limit");

                            CheckingAccount ca = new CheckingAccount(id, holderName, password, overdraftLimit);
                            ca.setBalance(balance);

                            return Optional.of(ca);
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

                            SavingsAccount sa = new SavingsAccount(id, holderName, password, interestRate);
                            sa.setBalance(balance);

                            return Optional.of(sa);
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
        String sqlFindAll = "SELECT * FROM accounts";

        try(Connection connection = DriverManager.getConnection(url, user, passwordSQL);
            PreparedStatement stmt = connection.prepareStatement(sqlFindAll)){

            List<Account> list = new ArrayList<>();
            ResultSet result = stmt.executeQuery();

            while(result.next()){
                String holderName = result.getString("holder_name");
                String password = result.getString("password");
                String accountType = result.getString("account_type");
                String id = result.getString("account_number");
                double balance = result.getDouble("balance");

                if(accountType.equals("CHECKING")){
                    String sqlChecking = "SELECT overdraft_limit FROM checking_accounts WHERE account_number = ?";

                    try(PreparedStatement stmt2 = connection.prepareStatement(sqlChecking)){
                        stmt2.setString(1, id);
                        ResultSet result2 = stmt2.executeQuery();

                        if(result2.next()){
                            double overdraftLimit = result2.getDouble("overdraft_limit");

                            CheckingAccount ca = new CheckingAccount(id, holderName, password, overdraftLimit);
                            ca.setBalance(balance);

                            list.add(ca);
                        }
                    }
                } else{
                    String sqlSavings = "SELECT interest_rate FROM savings_accounts WHERE account_number = ?";

                    try(PreparedStatement stmt3 = connection.prepareStatement(sqlSavings)){
                        stmt3.setString(1,id);
                        ResultSet result3 = stmt3.executeQuery();

                        if(result3.next()){
                            double interestRate = result3.getDouble("interest_rate");

                            SavingsAccount sa = new SavingsAccount(id, holderName, password, interestRate);
                            sa.setBalance(balance);

                            list.add(sa);
                        }
                    }
                }
            }
            return list;
        }catch(SQLException e){
            throw new RuntimeException("Failed to find any accounts: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(String id) {
        String sqlCheckingDelete = "DELETE FROM checking_accounts WHERE account_number = ?";
        String sqlSavingsDelete = "DELETE FROM savings_accounts WHERE account_number = ?";
        String sqlAccountDelete = "DELETE FROM accounts WHERE account_number = ?";

        try(Connection connection = DriverManager.getConnection(url, user, passwordSQL)) {
            try (PreparedStatement stmt = connection.prepareStatement(sqlCheckingDelete)) {
                stmt.setString(1, id);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(sqlSavingsDelete)) {
                stmt.setString(1, id);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = connection.prepareStatement(sqlAccountDelete)) {
                stmt.setString(1, id);
                stmt.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException("Failed to delete account: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(String id) {
        String sqlExistsById = "SELECT * FROM accounts WHERE account_number = ?";

        try(Connection connection = DriverManager.getConnection(url, user, passwordSQL);
            PreparedStatement stmt = connection.prepareStatement(sqlExistsById)){

            stmt.setString(1, id);
            ResultSet result = stmt.executeQuery();

            if(result.next()){
                return true;
            }

        }catch (SQLException e) {
            throw new RuntimeException("Failed to check if account exists: " + e.getMessage(), e);
        }
        return false;
    }

}