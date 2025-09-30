package com.example.banking.controller;


import com.example.banking.dto.AccountDto;
import com.example.banking.dto.CreateAccountRequestDto;
import com.example.banking.dto.UpdateBalanceRequestDto;
import com.example.banking.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountRequestDto request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountDto> deposit(@Valid @RequestBody UpdateBalanceRequestDto request) {
        return ResponseEntity.ok(accountService.deposit(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDto> withdraw(@Valid @RequestBody UpdateBalanceRequestDto request) {
        return ResponseEntity.ok(accountService.withdraw(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<AccountDto>> getAccountsByUserId(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId, pageable));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }
}
