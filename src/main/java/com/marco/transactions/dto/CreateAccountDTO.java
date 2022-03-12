package com.marco.transactions.dto;

import com.marco.transactions.domain.Account;

public class CreateAccountDTO {

    private String documentNumber;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Account toDomain() {
        var account = new Account();
        account.setDocumentNumber(this.getDocumentNumber());
        return account;
    }
}
