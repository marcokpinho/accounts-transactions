package com.marco.transactions.domain;

import com.marco.transactions.dto.TransactionDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @NotNull
    private OperationType operationType;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
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
        dto.setAccountId(this.getAccount().getAccountId());
        dto.setOperationTypeId(this.getOperationType().getOperationTypeId());
        dto.setAmount(this.getAmount());
        dto.setEventDate(this.getEventDate());
        return dto;
    }
}
