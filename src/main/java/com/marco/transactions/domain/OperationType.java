package com.marco.transactions.domain;

import java.util.stream.Stream;

public enum OperationType {
    COMPRA_A_VISTA(1, false),
    COMPRA_PARCELADA(2, false),
    SAQUE(3, false),
    PAGAMENTO(4, true);

    OperationType(long operationTypeId, Boolean isPayment) {
        this.operationTypeId = operationTypeId;
        this.isPayment = isPayment;
    }

    private long operationTypeId;
    private Boolean isPayment;

    public static OperationType fromValue(long id) {
        return Stream.of(OperationType.values())
                .filter(operationType -> operationType.operationTypeId == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Não existe um tipo de operação com id %d", id)));
    }


    public long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public Boolean getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(Boolean isPayment) {
        this.isPayment = isPayment;
    }
}
