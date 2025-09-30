package com.example.banking.service;


import com.example.banking.dto.AccountDto;
import com.example.banking.dto.CreateAccountRequestDto;
import com.example.banking.dto.UpdateBalanceRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    AccountDto createAccount(CreateAccountRequestDto request);
    AccountDto deposit(UpdateBalanceRequestDto request);
    AccountDto withdraw(UpdateBalanceRequestDto request);
    Page<AccountDto> getAccountsByUserId(Long userId, Pageable pageable);
    AccountDto getAccountById(Long accountId);
}
