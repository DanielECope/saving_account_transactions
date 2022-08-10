package com.nttdata.saving_account_transactions.controller;

import com.nttdata.saving_account_transactions.models.SavingAccountTransaction;
import com.nttdata.saving_account_transactions.service.SavingAccountTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/SavingAccountTransaction")
public class SavingAccountTransactionController {

    @Autowired
    SavingAccountTransactionService service;

    @GetMapping("/list")
    public Flux<SavingAccountTransaction> list(){
        log.info("Saving_account: controller list() method ");
        return service.findAll();
    }
    @GetMapping("/list/{id}")
    public Mono<SavingAccountTransaction> findById(String id){
        return service.findById(id);
    }
    @GetMapping("/NumberAccount/{numberAccount}")
    public Mono<SavingAccountTransaction> findBySavingAccountNumberAccount(String numberAccount){
        return service.findById(numberAccount);
    }
    @PostMapping("/create")
    public Mono<SavingAccountTransaction> create(@RequestBody SavingAccountTransaction svt) {
        return service.create(svt);
    }
}
