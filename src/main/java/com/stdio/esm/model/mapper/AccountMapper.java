package com.stdio.esm.model.mapper;

import com.stdio.esm.dto.AccountDto;
import com.stdio.esm.model.Account;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses = {EmployeeMapper.class, RoleMapper.class})
public interface AccountMapper {


    Account accountDtoToAccount(AccountDto accountDto);


    AccountDto accountToAccountDto(Account account);

    List<AccountDto> accountListToAccountDtoList(List<Account> accountList);

}
