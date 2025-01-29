package org.seerbit.controller;

import lombok.RequiredArgsConstructor;
import org.seerbit.dto.request.TransactionRequest;
import org.seerbit.models.Statistic;
import org.seerbit.service.TransactionService;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<?> transaction(@RequestBody TransactionRequest request) {
        transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<Statistic> getStatistics() {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.getStatistic());
    }

    @DeleteMapping("/transactions")
    public ResponseEntity<?> deleteTransactions() {
        return ResponseEntity.noContent().build();
    }
}
