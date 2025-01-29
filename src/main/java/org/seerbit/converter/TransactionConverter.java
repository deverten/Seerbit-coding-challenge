package org.seerbit.converter;


import org.seerbit.dto.request.TransactionRequest;
import org.seerbit.models.Transaction;

public class TransactionConverter {

    public static Transaction transactionConverter(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setTimeStamp(transactionRequest.getTimeStamp());
        return transaction;
    }
}
