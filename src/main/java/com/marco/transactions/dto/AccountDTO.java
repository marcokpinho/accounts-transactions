package com.marco.transactions.dto;

import com.marco.transactions.domain.Account;

public class AccountDTO {

    private long accountId;
    private String documentNumber;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Account toDomain() {
        var account = new Account();
        account.setDocumentNumber(this.getDocumentNumber());
        account.setAccountId(this.getAccountId());
        return account;
    }
}
