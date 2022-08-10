package com.nttdata.saving_account_transactions.repository;

import com.nttdata.saving_account_transactions.models.SavingAccountTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SavingAccountTransactionReporitory extends ReactiveMongoRepository<SavingAccountTransaction,String> {
    public Mono<SavingAccountTransaction> findBySavingAccountAccountNumber(String numberAccount);
    public Flux<SavingAccountTransaction> findAllBySavingAccountAccountNumber(String accountNumber);

}
