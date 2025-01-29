package org.seerbit.service;

import org.seerbit.dto.request.TransactionRequest;
import org.seerbit.models.Statistic;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;

public interface TransactionService {

    void createTransaction(TransactionRequest request);

    Statistic getStatistic();

    void deleteTransaction(TransactionRequest request);


}
