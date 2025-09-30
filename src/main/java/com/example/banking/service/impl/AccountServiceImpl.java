package com.example.banking.service.impl;


import com.example.banking.dto.AccountDto;
import com.example.banking.dto.CreateAccountRequestDto;
import com.example.banking.dto.UpdateBalanceRequestDto;
import com.example.banking.entity.Account;
import com.example.banking.entity.User;
import com.example.banking.exception.BadRequestException;
import com.example.banking.exception.UserNotFoundException;
import com.example.banking.mapper.AccountMapper;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.UserRepository;
import com.example.banking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepo;
    private final UserRepository userRepo;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public AccountDto createAccount(CreateAccountRequestDto request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found: id=" + request.getUserId()));

        Account account = accountMapper.toEntity(request);
        account.setUser(user);

        return accountMapper.toDto(accountRepo.save(account));
    }

    @Override
    @Transactional
    public AccountDto deposit(UpdateBalanceRequestDto request) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Deposit amount must be greater than 0");
        }

        Account account = accountRepo.findById(request.getAccountId())
                .orElseThrow(() -> new BadRequestException("Account not found: id=" + request.getAccountId()));

        account.setBalance(account.getBalance().add(request.getAmount()));

        return accountMapper.toDto(accountRepo.save(account));
    }

    @Override
    @Transactional
    public AccountDto withdraw(UpdateBalanceRequestDto request) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Withdraw amount must be greater than 0");
        }

        Account account = accountRepo.findById(request.getAccountId())
                .orElseThrow(() -> new BadRequestException("Account not found: id=" + request.getAccountId()));

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BadRequestException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));

        return accountMapper.toDto(accountRepo.save(account));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountDto> getAccountsByUserId(Long userId, Pageable pageable) {
        return accountRepo.findByUserId(userId, pageable)
                .map(accountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountById(Long accountId) {
        return accountRepo.findById(accountId)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new BadRequestException("Account not found: id=" + accountId));
    }
}

