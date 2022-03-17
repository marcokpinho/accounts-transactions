package com.marco.transactions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.transactions.configuration.ObjectMapperConfiguration;
import com.marco.transactions.dto.CreateTransactionDTO;
import com.marco.transactions.dto.TransactionDTO;
import com.marco.transactions.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TransactionController.class)
@Import(ObjectMapperConfiguration.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void shouldThrowExceptionWhenTryPostEmptyTransaction() throws Exception {
        var emptyBody = new CreateTransactionDTO();
        this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(emptyBody)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Problema ao processar requisição"))
                .andExpect(jsonPath("$.validations[*]", hasItems(
                        "Necessário informar account_id",
                        "Necessário informar operation_type_id"
                )));

        verifyNoInteractions(transactionService);
    }

    @Test
    public void shouldThrowExceptionWhenTryPostNegativeAmount() throws Exception {
        var body = new CreateTransactionDTO();
        body.setAccountId(1L);
        body.setOperationTypeId(4L);
        body.setAmount(BigDecimal.valueOf(-10));
        this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Problema ao processar requisição"))
                .andExpect(jsonPath("$.validations[*]", hasItem("Amount deve ser maior que zero")));

        verifyNoInteractions(transactionService);
    }

    @Test
    public void shouldCreateTransactionSuccessfully() throws Exception {
        var body = new CreateTransactionDTO();
        body.setAccountId(1L);
        body.setOperationTypeId(3L);
        body.setAmount(BigDecimal.valueOf(10));

        var transactionDto = new TransactionDTO();
        transactionDto.setTransactionId(1L);
        transactionDto.setAccountId(body.getAccountId());
        transactionDto.setOperationTypeId(body.getOperationTypeId());
        transactionDto.setAmount(body.getAmount());
        transactionDto.setEventDate(LocalDateTime.of(2022, 3, 15, 12, 20, 30));

        when(transactionService.save(any())).thenReturn(transactionDto);

        this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("transaction_id").value(1))
                .andExpect(jsonPath("account_id").value(1))
                .andExpect(jsonPath("operation_type_id").value(3))
                .andExpect(jsonPath("amount").value(10))
                .andExpect(jsonPath("event_date").value("2022-03-15T12:20:30"));

        verify(transactionService).save(any());
    }

}