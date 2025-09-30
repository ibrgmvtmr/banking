package com.example.banking.mapper;


import com.example.banking.dto.CreateUserRequestDto;
import com.example.banking.dto.UserDto;
import com.example.banking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {


    User toEntity(CreateUserRequestDto createUserRequestDto);

    UserDto toDto(User user);

}

