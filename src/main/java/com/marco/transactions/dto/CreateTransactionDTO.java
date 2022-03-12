package com.marco.transactions.dto;

import com.marco.transactions.domain.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateTransactionDTO {

    private Long accountId;
    private Long operationTypeId;
    private BigDecimal amount;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Transaction toDomain() {
        var transaction = new Transaction();
        transaction.setAccountId(this.getAccountId());
        transaction.setOperationTypeId(this.getOperationTypeId());
        transaction.setAmount(this.getAmount());
        transaction.setEventDate(LocalDateTime.now());
        return transaction;
    }
}
