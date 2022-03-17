package com.marco.transactions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.transactions.configuration.ObjectMapperConfiguration;
import com.marco.transactions.dto.AccountDTO;
import com.marco.transactions.dto.CreateAccountDTO;
import com.marco.transactions.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@Import(ObjectMapperConfiguration.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Test
    public void shouldThrowExceptionWhenTryPostAccountWithoutDocumentNumber() throws Exception {
        var emptyBody = new CreateAccountDTO();
        this.mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(emptyBody)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Problema ao processar requisição"))
                .andExpect(jsonPath("$.validations[*]", hasItem("Necessário informar document_number")));

        verifyNoInteractions(accountService);
    }

    @Test
    public void shouldCreateAccountSuccessfully() throws Exception {
        var body = new CreateAccountDTO();
        body.setDocumentNumber("123");

        var accountDto = new AccountDTO();
        accountDto.setAccountId(1L);
        accountDto.setDocumentNumber(body.getDocumentNumber());

        when(accountService.save(any())).thenReturn(accountDto);

        this.mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("account_id").value(1))
                .andExpect(jsonPath("document_number").value("123"));

        verify(accountService).save(any());
    }

    @Test
    public void shouldThrowExceptionWhenTryGetNonexistentAccount() throws Exception {
        this.mockMvc.perform(get("/accounts/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Não foi possível encontrar uma conta com id 1")));

        verify(accountService).findById(1);
    }

    @Test
    public void shouldGetAccountSuccessfully() throws Exception {
        var accountDto = new AccountDTO();
        accountDto.setAccountId(1L);
        accountDto.setDocumentNumber("123");

        when(accountService.findById(accountDto.getAccountId())).thenReturn(Optional.of(accountDto));

        this.mockMvc.perform(get("/accounts/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("account_id").value(1))
                .andExpect(jsonPath("document_number").value("123"));

        verify(accountService).findById(1);
    }
}