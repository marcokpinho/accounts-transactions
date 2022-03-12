package com.marco.transactions.controller;

import com.marco.transactions.dto.CreateTransactionDTO;
import com.marco.transactions.dto.TransactionDTO;
import com.marco.transactions.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public TransactionDTO createTransaction(@RequestBody CreateTransactionDTO createTransactionDTO) {
        return transactionService.save(createTransactionDTO);
    }
}
