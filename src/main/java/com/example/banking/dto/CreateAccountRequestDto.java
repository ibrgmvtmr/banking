package com.example.banking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequestDto {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "User number is required")
    private String number;

}
