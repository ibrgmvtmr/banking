package com.example.banking.service.impl;

import com.example.banking.dto.CreateUserRequestDto;
import com.example.banking.dto.UserDto;
import com.example.banking.exception.BadRequestException;
import com.example.banking.exception.UserNotFoundException;
import com.example.banking.mapper.UserMapper;
import com.example.banking.repository.UserRepository;
import com.example.banking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(CreateUserRequestDto createUserRequestDto) {
        if (userRepo.existsByEmail(createUserRequestDto.getEmail())) {
            throw new BadRequestException("Email already in use");
        }
        return  userMapper.toDto(userRepo.save(userMapper.toEntity(createUserRequestDto)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        return userRepo.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found: id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getByEmail(String email) {
        return userRepo.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found: email=" + email));
    }
}
