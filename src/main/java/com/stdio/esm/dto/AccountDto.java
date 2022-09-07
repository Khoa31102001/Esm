package com.stdio.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Data
@JsonRootName(value = "account")
public class AccountDto implements Serializable {
    private static final long serialVersionUID = -813028390687762272L;
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]+$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]+$";



    @NotNull(groups = {OnUpdate.class})
    @Null(groups = OnCreate.class)
    private final Long id;

    @NotBlank(message = "{message.login.username.required}")
    @Size(min = 6, max = 20, message = "Username length from 6 to 20 characters")
    @Pattern(regexp = USERNAME_PATTERN, message = "Username are only allowed alphanumeric character")
    private final String username;

    @NotBlank(message = "message.login.password.required")
    @Size(min = 6, max = 20, message = "Password length from 6 to 20 characters")
    @Pattern(regexp = PASSWORD_PATTERN, message = "Passwords are only allowed alphanumeric character")
    private final String password;

    @JsonIgnore
    private final Instant createdAt;

    @JsonIgnore
    private final Instant modifyAt;

    @JsonIgnore
    private final Boolean deleteFlag;

    @NotEmpty
    private final Set<RoleDto> roles;
    
    private final EmployeeDto employee;

}
