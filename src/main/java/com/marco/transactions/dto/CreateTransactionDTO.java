package com.marco.transactions.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class CreateTransactionDTO {

    @NotNull
    private Long accountId;
    @NotNull
    private Long operationTypeId;
    @Positive
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

    public void setOperationType(Long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
