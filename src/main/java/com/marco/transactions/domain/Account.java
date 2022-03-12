package com.marco.transactions.domain;

import com.marco.transactions.dto.AccountDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @NotNull
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

    public AccountDTO toDto() {
        var dto = new AccountDTO();
        dto.setDocumentNumber(this.getDocumentNumber());
        dto.setAccountId(this.getAccountId());
        return dto;
    }
}
