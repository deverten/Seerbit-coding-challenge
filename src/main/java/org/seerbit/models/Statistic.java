package org.seerbit.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Statistic {
    private BigDecimal sum;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private long count;
}
