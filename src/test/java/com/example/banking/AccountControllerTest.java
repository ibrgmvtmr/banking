package com.example.banking;

import com.example.banking.controller.AccountController;
import com.example.banking.dto.AccountDto;
import com.example.banking.dto.CreateAccountRequestDto;
import com.example.banking.dto.UpdateBalanceRequestDto;
import com.example.banking.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    private AccountDto buildAccount(Long id, String number, BigDecimal balance, Long userId) {
        return new AccountDto(
                id,
                number,
                balance,
                userId,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now()
        );
    }

    @Test
    void createAccount_shouldReturnAccount() throws Exception {
        CreateAccountRequestDto request = new CreateAccountRequestDto(1L, "996222222222");
        AccountDto response = buildAccount(1L, "ACC-123", BigDecimal.valueOf(1000.0), 1L);

        Mockito.when(accountService.createAccount(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.number").value("ACC-123"))
                .andExpect(jsonPath("$.balance").value(1000.0))
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    void deposit_shouldIncreaseBalance() throws Exception {
        UpdateBalanceRequestDto request = new UpdateBalanceRequestDto(1L, BigDecimal.valueOf(200));
        AccountDto response = buildAccount(1L, "ACC-123", BigDecimal.valueOf(1200.0), 1L);

        Mockito.when(accountService.deposit(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1200.0));
    }

    @Test
    void withdraw_shouldDecreaseBalance() throws Exception {
        UpdateBalanceRequestDto request = new UpdateBalanceRequestDto(1L, BigDecimal.valueOf(300));
        AccountDto response = buildAccount(1L, "ACC-123", BigDecimal.valueOf(700.0), 1L);

        Mockito.when(accountService.withdraw(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(700.0));
    }

    @Test
    void getAccountsByUserId_shouldReturnPage() throws Exception {
        AccountDto account = buildAccount(1L, "ACC-999", BigDecimal.valueOf(500.0), 1L);
        Mockito.when(accountService.getAccountsByUserId(eq(1L), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(account)));

        mockMvc.perform(get("/api/v1/accounts/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].balance").value(500.0))
                .andExpect(jsonPath("$.content[0].number").value("ACC-999"));
    }

    @Test
    void getAccountById_shouldReturnAccount() throws Exception {
        AccountDto account = buildAccount(2L, "ACC-555", BigDecimal.valueOf(1500.0), 1L);
        Mockito.when(accountService.getAccountById(2L)).thenReturn(account);

        mockMvc.perform(get("/api/v1/accounts/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.balance").value(1500.0))
                .andExpect(jsonPath("$.number").value("ACC-555"));
    }
}
