package com.marco.transactions.service;


import com.marco.transactions.domain.Account;
import com.marco.transactions.dto.AccountDTO;
import com.marco.transactions.dto.CreateAccountDTO;
import com.marco.transactions.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO save(CreateAccountDTO createAccountDTO) {
        return accountRepository.save(createAccountDTO.toDomain()).toDto();
    }

    public Optional<AccountDTO> findById(long accountId) {
        return accountRepository.findById(accountId).map(Account::toDto);
    }
}
