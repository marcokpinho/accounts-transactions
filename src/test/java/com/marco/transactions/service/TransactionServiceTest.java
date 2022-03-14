package com.marco.transactions.service;

import com.marco.transactions.domain.Account;
import com.marco.transactions.dto.CreateTransactionDTO;
import com.marco.transactions.repository.AccountRepository;
import com.marco.transactions.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public final void shouldNotSaveTransactionWithInvalidAccountId() {
        var createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(123L);
        createTransactionDTO.setAmount(BigDecimal.TEN);
        createTransactionDTO.setOperationTypeId(1L);

        when(accountRepository.findById(createTransactionDTO.getAccountId())).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> transactionService.save(createTransactionDTO));

        assertEquals("Não existe uma conta para o account_id informado", exception.getMessage());

        verify(accountRepository).findById(createTransactionDTO.getAccountId());
        verify(transactionRepository, times(0)).save(any());
    }

    @Test
    public final void shouldNotSaveTransactionWithInvalidOperationType() {
        var createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(123L);
        createTransactionDTO.setAmount(BigDecimal.TEN);
        createTransactionDTO.setOperationTypeId(5L);

        when(accountRepository.findById(createTransactionDTO.getAccountId())).thenReturn(Optional.of(new Account()));

        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> transactionService.save(createTransactionDTO));

        assertEquals("Não existe um tipo de operação com id 5", exception.getMessage());

        verify(accountRepository).findById(createTransactionDTO.getAccountId());
        verify(transactionRepository, times(0)).save(any());
    }

    @Test
    public final void shouldSaveTransactionSuccessfully() {
        var createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(123L);
        createTransactionDTO.setAmount(BigDecimal.TEN);
        createTransactionDTO.setOperationTypeId(1L);

        var account = new Account();
        account.setAccountId(123L);

        when(accountRepository.findById(createTransactionDTO.getAccountId())).thenReturn(Optional.of(account));
        when(transactionRepository.save(any())).then(returnsFirstArg());

        var transactionDTO = transactionService.save(createTransactionDTO);

        assertEquals(account.getAccountId(), transactionDTO.getAccountId());
        assertEquals(createTransactionDTO.getOperationTypeId(), transactionDTO.getOperationTypeId());
        assertEquals(createTransactionDTO.getAmount(), transactionDTO.getAmount());
        assertEquals(LocalDate.now(), transactionDTO.getEventDate().toLocalDate());

        verify(accountRepository).findById(createTransactionDTO.getAccountId());
        verify(transactionRepository).save(any());
    }

    @Test
    public final void shouldSavePaymentTransactionSuccessfully() {
        var createTransactionDTO = new CreateTransactionDTO();
        createTransactionDTO.setAccountId(123L);
        createTransactionDTO.setAmount(BigDecimal.TEN);
        createTransactionDTO.setOperationTypeId(4L);

        var account = new Account();
        account.setAccountId(123L);

        when(accountRepository.findById(createTransactionDTO.getAccountId())).thenReturn(Optional.of(account));
        when(transactionRepository.save(any())).then(returnsFirstArg());

        var transactionDTO = transactionService.save(createTransactionDTO);

        assertEquals(account.getAccountId(), transactionDTO.getAccountId());
        assertEquals(createTransactionDTO.getOperationTypeId(), transactionDTO.getOperationTypeId());
        assertEquals(createTransactionDTO.getAmount().negate(), transactionDTO.getAmount());
        assertEquals(LocalDate.now(), transactionDTO.getEventDate().toLocalDate());

        verify(accountRepository).findById(createTransactionDTO.getAccountId());
        verify(transactionRepository).save(any());
    }

}