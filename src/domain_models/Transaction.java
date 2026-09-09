package domain_models;

import java.time.LocalDateTime;

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
) {}