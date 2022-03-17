package com.marco.transactions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.transactions.configuration.ObjectMapperConfiguration;
import com.marco.transactions.dto.CreateTransactionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@Import(ObjectMapperConfiguration.class)
public class TransactionControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(statements = {"insert into account (account_id, document_number) values (5, '123456789')"})
    public void shouldCreateTransactionSuccessfully() throws Exception {
        var body = new CreateTransactionDTO();
        body.setAccountId(5L);
        body.setOperationTypeId(3L);
        body.setAmount(BigDecimal.valueOf(10.50));

        this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("transaction_id", notNullValue()))
                .andExpect(jsonPath("account_id").value(5))
                .andExpect(jsonPath("operation_type_id").value(3))
                .andExpect(jsonPath("amount").value(-10.50))
                .andExpect(jsonPath("event_date", notNullValue()));
    }

    @Test
    public void shouldNotPermitCreateTransactionIfAccountNotExist() throws Exception {
        var body = new CreateTransactionDTO();
        body.setAccountId(5L);
        body.setOperationTypeId(3L);
        body.setAmount(BigDecimal.valueOf(10.50));

        this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Não existe uma conta para o account_id informado"));
    }
}