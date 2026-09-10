package domain_models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * a record of a single operation
 * @param transactionId
 * @param type
 * @param amount
 * @param timestamp
 */
public record Transaction(
        String transactionId,
        String type,
        double amount,
        LocalDateTime timestamp
) {
    @Override
    public String toString(){
        return "[" + transactionId + "] " + type + ": " +
                String.format("%.2f", amount) + " (" +
                timestamp.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + ")";
    }
}