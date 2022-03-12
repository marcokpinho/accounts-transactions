package com.marco.transactions.service;

import com.marco.transactions.dto.CreateTransactionDTO;
import com.marco.transactions.dto.TransactionDTO;
import com.marco.transactions.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionDTO save(CreateTransactionDTO createTransactionDTO) {
        return transactionRepository.save(createTransactionDTO.toDomain()).toDto();
    }
}
