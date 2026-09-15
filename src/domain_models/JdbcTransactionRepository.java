package domain_models;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;

public class JdbcTransactionRepository {
    private final String url = "jdbc:mysql://localhost:3306/banking_system";
    private final String user = "root";
    private final String password = "My_Mysql1";
    private final String sqlHistory = "INSERT INTO transactions (transaction_id, account_number, type, amount, timestamp) VALUES (?, ?, ?, ?, ?)";

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
}
