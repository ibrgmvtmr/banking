package com.example.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDto(
        Long id,
        String number,
        BigDecimal balance,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}