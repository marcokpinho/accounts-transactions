package com.marco.transactions.domain;

import com.marco.transactions.dto.TransactionDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;

    @NotNull
    private long accountId;

    @NotNull
    private long operationTypeId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDateTime eventDate;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public TransactionDTO toDto() {
        var dto = new TransactionDTO();
        dto.setTransactionId(this.getTransactionId());
        dto.setAccountId(this.getAccountId());
        dto.setOperationTypeId(this.getOperationTypeId());
        dto.setAmount(this.getAmount());
        dto.setEventDate(this.getEventDate());
        return dto;
    }
}
