package com.example.banking.mapper;


import com.example.banking.dto.AccountDto;
import com.example.banking.dto.CreateAccountRequestDto;
import com.example.banking.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AccountMapper {

    @Mapping(target = "number", source = "number")
    Account toEntity(CreateAccountRequestDto dto);

    @Mapping(target = "userId", expression = "java(account.getUser() != null ? account.getUser().getId() : null)")
    AccountDto toDto(Account account);
}

