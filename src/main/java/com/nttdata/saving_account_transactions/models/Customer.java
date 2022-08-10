package com.nttdata.saving_account_transactions.models;

import lombok.Data;

@Data
public class Customer {

    String id;
    String name;
    String lastName;
    TypeCustomer typeCustomer;
    String document;
}
