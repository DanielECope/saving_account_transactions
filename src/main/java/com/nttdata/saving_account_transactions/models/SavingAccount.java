package com.nttdata.saving_account_transactions.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@Builder
public class SavingAccount {

    @Id
    @NotNull
    private String accountNumber;
    @NotNull
    @Indexed(unique = true)
    private Customer customer;
    @NotNull
    private float commission;
    @NotNull
    private int movement_limit;
    private int movementNumber;
    private float amountAvailable;
    private float amount;
    private LocalDateTime registration_date;
}
