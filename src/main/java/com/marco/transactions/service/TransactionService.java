package com.marco.transactions.service;

import com.marco.transactions.domain.Account;
import com.marco.transactions.domain.OperationType;
import com.marco.transactions.domain.Transaction;
import com.marco.transactions.dto.CreateTransactionDTO;
import com.marco.transactions.dto.TransactionDTO;
import com.marco.transactions.repository.AccountRepository;
import com.marco.transactions.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public TransactionDTO save(CreateTransactionDTO createTransactionDTO) {
        var transaction = this.dtoToDomain(createTransactionDTO);

        return transactionRepository.save(transaction).toDto();
    }

    private Transaction dtoToDomain(CreateTransactionDTO createTransactionDTO) {
        Account account = accountRepository.findById(createTransactionDTO.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Não existe uma conta para o account_id informado"));

        var transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setOperationType(OperationType.fromValue(createTransactionDTO.getOperationTypeId()));

        if (transaction.getOperationType().getIsPayment()) {
            transaction.setAmount(createTransactionDTO.getAmount().negate());
        } else {
            transaction.setAmount(createTransactionDTO.getAmount());
        }
        transaction.setEventDate(LocalDateTime.now());
        return transaction;
    }
}
