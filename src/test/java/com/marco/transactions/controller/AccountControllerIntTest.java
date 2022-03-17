package com.marco.transactions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.transactions.configuration.ObjectMapperConfiguration;
import com.marco.transactions.dto.CreateAccountDTO;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(ObjectMapperConfiguration.class)
@Transactional
@Rollback
public class AccountControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateAccountSuccessfully() throws Exception {
        var body = new CreateAccountDTO();
        body.setDocumentNumber("123");

        this.mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("account_id", notNullValue()))
                .andExpect(jsonPath("document_number").value("123"));
    }

    @Test
    public void shouldNotPermitTwoAccountWithSameDocumentNumber() throws Exception {
        var body = new CreateAccountDTO();
        body.setDocumentNumber("123");

        this.mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("account_id", notNullValue()))
                .andExpect(jsonPath("document_number").value("123"));

        this.mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Já existe uma conta para o número de documento 123"));
    }

    @Test
    @Sql(statements = {"insert into account (account_id, document_number) values (5, '123456789')"})
    public void shouldGetAccountSuccessfully() throws Exception {
        this.mockMvc.perform(get("/accounts/5").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("account_id", notNullValue()))
                .andExpect(jsonPath("document_number").value("123456789"));
    }
}