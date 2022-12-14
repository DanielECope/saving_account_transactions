package com.nttdata.saving_account_transactions.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@Document("SavingAccountTransaction")
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccountTransaction {
    @Id
    private String id;
    @NotNull
    private SavingAccount savingAccount;
    @NotNull
    private TypeTransaction typeTransaction;
    @NotNull
    private float amount;
    private float commissionAmount;
    private LocalDateTime transactionDateTime;


}
