package domain_models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionRepository {
    private final String url = "jdbc:mysql://localhost:3306/banking_system";
    private final String user = "root";
    private final String password = "My_Mysql1";
    private final String sqlHistory = "INSERT INTO transactions (transaction_id, account_number, type, amount, timestamp) VALUES (?, ?, ?, ?, ?)";
    private final String sqlCount = "SELECT count(*) FROM transactions";
    private final String sqlHistoryList = "SELECT * FROM transactions WHERE account_number = ?";

    public int count(){
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(sqlCount);
            ResultSet rs = stmt.executeQuery()){

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void save(Account account, Transaction transaction){
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(sqlHistory)){


            stmt.setString(1, transaction.getTransactionId());
            stmt.setString(2,account.getAccountNumber());
            stmt.setString(3, transaction.getType());
            stmt.setDouble(4, transaction.getAmount());
            stmt.setTimestamp(5, Timestamp.valueOf(transaction.getTime()));

            stmt.executeUpdate();

        }catch(SQLException e){
            throw new RuntimeException("Failed to save transaction history: " + e.getMessage(), e);
        }
    }

    public List<Transaction> findByAccountNumber(String accountNumber){

        List<Transaction> transactionList = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = connection.prepareStatement(sqlHistoryList)){

            stmt.setString(1,accountNumber);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String id = rs.getString("transaction_id");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                Timestamp time = rs.getTimestamp("timestamp");

                Transaction t = new Transaction(id, type, amount, time.toLocalDateTime());
                transactionList.add(t);
            }

        }catch(SQLException e){
            throw new RuntimeException("Failed to retrieve transaction history" + e.getMessage() , e);
        }
        return transactionList;
    }
}
