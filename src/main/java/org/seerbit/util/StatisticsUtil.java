package org.seerbit.util;

import lombok.extern.slf4j.Slf4j;
import org.seerbit.models.Statistic;
import org.seerbit.models.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class StatisticsUtil {

    public static void generateStatistic(Statistic statistic, ConcurrentLinkedQueue<Transaction> transactions) {

        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal avg = BigDecimal.ZERO;
        BigDecimal max = BigDecimal.ZERO;
        BigDecimal min = BigDecimal.ZERO;
        long count = 0;

        try {
            for (Transaction transaction : transactions) {
                if (count == 0) {
                    max = transaction.getAmount();
                    min = transaction.getAmount();
                } else {
                    max = max.max(transaction.getAmount());  //compares incoming amount to perviously set amount and assigns the maximum to the max variable
                    min = min.min(transaction.getAmount());  //compares incoming amount to perviously set amount and assigns the minimum to the max variable
                }
                sum = sum.add(transaction.getAmount());
                count++;
            }

            avg = sum.divide(new BigDecimal(count), 2, RoundingMode.HALF_UP);

            statistic.setMax(max);
            statistic.setMin(min);
            statistic.setSum(sum);
            statistic.setAvg(avg);
            statistic.setCount(count);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

}
