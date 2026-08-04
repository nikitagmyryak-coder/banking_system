package domain_models;

import java.time.LocalDateTime;

public record Transaction(
        String transactionId,
        String type,
        double amount,
        LocalDateTime timestamp
) {}