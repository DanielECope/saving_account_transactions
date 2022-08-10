package com.nttdata.saving_account_transactions.service;

import com.nttdata.saving_account_transactions.models.SavingAccountTransaction;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SavingAccountTransactionService {
    public Flux<SavingAccountTransaction> findAll();
    public Mono<SavingAccountTransaction> findById(String id);
    public Mono<SavingAccountTransaction> create(SavingAccountTransaction t);
    public Mono<SavingAccountTransaction> findBySavingAccountNumberAccount(String NumberAccount);

}
