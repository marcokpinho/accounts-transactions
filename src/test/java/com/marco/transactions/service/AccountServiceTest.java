package com.marco.transactions.service;

import com.marco.transactions.domain.Account;
import com.marco.transactions.dto.CreateAccountDTO;
import com.marco.transactions.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public final void shouldNotSaveAccountWithDuplicateDocumentNumber() {
        var documentNumber = "1234567890";
        when(accountRepository.findByDocumentNumber(documentNumber)).thenReturn(Optional.of(new Account()));

        var createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setDocumentNumber(documentNumber);

        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.save(createAccountDTO));

        assertEquals("Já existe uma conta para o número de documento 1234567890", exception.getMessage());

        verify(accountRepository).findByDocumentNumber(documentNumber);
        verify(accountRepository, times(0)).save(any());
    }

    @Test
    public final void shouldSaveSuccessfully() {
        var documentNumber = "1234567890";
        when(accountRepository.findByDocumentNumber(documentNumber)).thenReturn(Optional.empty());
        when(accountRepository.save(any())).then(returnsFirstArg());

        var createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setDocumentNumber(documentNumber);

        var accountDTO = accountService.save(createAccountDTO);

        assertEquals(accountDTO.getDocumentNumber(), documentNumber);

        verify(accountRepository).findByDocumentNumber(documentNumber);
        verify(accountRepository).save(any());
    }

    @Test
    public final void shouldFindByIdSuccessfully() {
        var account = new Account();
        account.setAccountId(123);
        account.setDocumentNumber("123456789");

        when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));

        var accountDTO = accountService.findById(account.getAccountId()).get();

        assertEquals(accountDTO.getAccountId(), account.getAccountId());
        assertEquals(accountDTO.getDocumentNumber(), account.getDocumentNumber());

        verify(accountRepository).findById(account.getAccountId());
    }

}