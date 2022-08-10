package com.nttdata.saving_account_transactions.serviceImpl;

import com.nttdata.saving_account_transactions.models.SavingAccount;
import com.nttdata.saving_account_transactions.models.SavingAccountTransaction;
import com.nttdata.saving_account_transactions.models.TypeTransaction;
import com.nttdata.saving_account_transactions.repository.SavingAccountTransactionReporitory;
import com.nttdata.saving_account_transactions.service.SavingAccountTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactionSavingAccount")
@Slf4j
public class SavingAccountTransactionImpl implements SavingAccountTransactionService {
    private final WebClient webClientCustomer= WebClient.create("http://localhost:8080/customer");
    private final WebClient webClientSavingAccount= WebClient.create("http://localhost:8080/api/SavingAccount");

    @Autowired
    SavingAccountTransactionReporitory repository;

    @Override
    public Flux<SavingAccountTransaction> findAll(){
        return repository.findAll();
    }
    @Override
    public Mono<SavingAccountTransaction> findById(String id){
        return repository.findById(id);
    }
    @Override
    public Mono<SavingAccountTransaction> findBySavingAccountNumberAccount(String numberAccount){
        return repository.findById(numberAccount);
    }

  @Override
    public Mono<SavingAccountTransaction> create(SavingAccountTransaction t) {
      Mono<SavingAccount> savingAccountMono = findBySavingAccountMono(t.getSavingAccount().getAccountNumber());

      SavingAccount savingAccount = null;
      //para enviar al webclient
        try{
          savingAccount.setAccountNumber(savingAccountMono.block().getAccountNumber());
          savingAccount.setCommission(savingAccountMono.block().getCommission());
          savingAccount.setCustomer(savingAccountMono.block().getCustomer());
          savingAccount.setAmountAvailable(savingAccountMono.block().getAmountAvailable());
          savingAccount.setMovementNumber(savingAccountMono.block().getMovementNumber());
          savingAccount.setMovement_limit(savingAccountMono.block().getMovement_limit());
          t.setSavingAccount(savingAccount);

          if (savingAccount.getMovement_limit() >= savingAccount.getMovementNumber()) {
              throw new RuntimeException("Ha Excedido el limite de movimientos");
          }
          savingAccount.setMovementNumber(savingAccount.getMovementNumber() + 1);
          if (t.getTypeTransaction() == TypeTransaction.IN) {
              savingAccount.setAmountAvailable(savingAccount.getAmount() + t.getAmount());
          } else {
              savingAccount.setAmountAvailable(savingAccount.getAmount() - t.getAmount());
          }
      }catch (Exception e){
            throw new RuntimeException("Nro de cuenta incorrecto");
        }
        /*
      repository.save(t).subscribe(a -> {
          this.updateSavingAccount(savingAccount);
      });

         */
      try{
          repository.save(t);
          this.updateSavingAccount(savingAccount);
      }catch (Exception e){
          throw new RuntimeException("Error");
      }

        return Mono.just(t);
    }



    public Mono<SavingAccount> findBySavingAccountMono(String accountNumber){
        log.info("SavingAccountTransactionImpl: implements findBySavingAccountMono() method : {}",accountNumber);
        return webClientCustomer.get().uri("/findByAccountNumber/{accountNumber}",accountNumber)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SavingAccount.class);
    }
    public Mono<SavingAccount> updateSavingAccount(SavingAccount sv){
        log.info("SavingAccountTransactionImpl: implements findBySavingAccountUpdate() method : {}",sv.toString());
        return webClientCustomer.put().uri("/update",sv)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SavingAccount.class);
    }

}
