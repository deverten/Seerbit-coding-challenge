package org.seerbit.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.seerbit.converter.TransactionConverter;
import org.seerbit.dto.request.TransactionRequest;
import org.seerbit.exception.ExpiredTimeStampException;
import org.seerbit.exception.NotParseableException;
import org.seerbit.models.Statistic;
import org.seerbit.models.Transaction;
import org.seerbit.service.TransactionService;
import org.seerbit.util.StatisticsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Value("${transaction.validity.time}")
    private long validTime;

    final ConcurrentLinkedQueue<Transaction> transactions = new ConcurrentLinkedQueue<Transaction>();

    final Object dbLock = new Object();


    @Override
    public void createTransaction(TransactionRequest request) {

        Transaction transaction = TransactionConverter.transactionConverter(request);

        try {
            Instant currentTime = Instant.now();  //current system timestamp
            Instant timestamp = request.getTimeStamp();  //timestamp of the incoming transaction

            BigDecimal amount = request.getAmount().setScale(2, RoundingMode.HALF_UP);
            transaction.setAmount(amount);

            if (timestamp.isBefore(currentTime.minusSeconds(validTime))) {
                log.error("Timestamp is older than valid time");
                throw new ExpiredTimeStampException("Transaction timestamp is older than 30 seconds.");
            }

            if (timestamp.isAfter(currentTime)) {
                log.error("Timestamp is in the future");
                throw new NotParseableException("Transaction timestamp is in the future.");
            }

            synchronized (dbLock) {
                //use of synchronized block to allow one request access the resource at a time
                log.info("Creating transaction {}", transaction.toString());
                transactions.add(transaction);
            }

        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw new NotParseableException(e.getMessage());
        }
    }

    @Override
    public Statistic getStatistic() {

        Statistic statistic = new Statistic();
        Instant validTimePeriod = Instant.now().minusSeconds(validTime);

        synchronized (dbLock) {
//            use of lock to isolate only transactions that occured within the last 30 seconds
            transactions.removeIf(txn -> txn.getTimeStamp().isBefore(validTimePeriod));
             StatisticsUtil.generateStatistic(statistic, transactions);
        }
        return statistic;
    }

    @Override
    public void deleteTransaction(TransactionRequest request) {
        synchronized (dbLock) {
            transactions.clear();
        }
    }
}
