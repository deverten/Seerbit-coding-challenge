package org.seerbit.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class Transaction {
    private BigDecimal amount;
    private Instant timeStamp;
}
