package org.seerbit.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TransactionRequest {

    @NotBlank
    private BigDecimal amount;

    @NotBlank
    private Instant timeStamp;
}
