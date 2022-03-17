package com.marco.transactions.controller;

import com.marco.transactions.dto.CreateTransactionDTO;
import com.marco.transactions.dto.TransactionDTO;
import com.marco.transactions.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new transaction")
    @PostMapping
    public TransactionDTO createTransaction(@Valid @RequestBody CreateTransactionDTO createTransactionDTO) {
        return transactionService.save(createTransactionDTO);
    }
}
