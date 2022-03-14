package com.marco.transactions.controller;

import com.marco.transactions.dto.AccountDTO;
import com.marco.transactions.dto.CreateAccountDTO;
import com.marco.transactions.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Create new account")
    @PostMapping
    public AccountDTO createAccount(@Valid @RequestBody CreateAccountDTO createAccountDTO) {
        return accountService.save(createAccountDTO);
    }

    @ApiOperation(value = "Get account by id")
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("accountId") long accountId) {

        var accountDto = accountService.findById(accountId);

        return accountDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
