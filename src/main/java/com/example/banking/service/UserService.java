package com.example.banking.service;


import com.example.banking.dto.CreateUserRequestDto;
import com.example.banking.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto createUser(CreateUserRequestDto createUserRequestDto);
    UserDto getUserById(Long id);
    Page<UserDto> getAllUsers(Pageable pageable);
    UserDto getByEmail(String email);
}
