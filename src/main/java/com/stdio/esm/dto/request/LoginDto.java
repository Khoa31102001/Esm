package com.stdio.esm.dto.request;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data

public class LoginDto implements Serializable {
    private static final long serialVersionUID = -5915363687697776634L;

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]+$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]+$";


    /**
     * Username validation:
     * 1. Not empty
     * 2. Length: 8-20
     * 3. Rule: Just contains alphanumeric character
     */
    @NotBlank(message = "{message.login.username.required}")
    @Size(min = 6, max = 20, message = "Username length from 8 to 20 characters")
    @Pattern(regexp = USERNAME_PATTERN, message = "Username are only allowed alphanumeric character")
    private final String username;

    /**
     * Password validation:
     * 1. Not empty
     * 2. Length: 8-20
     * 3. Rule: Just contains alphanumeric character
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password length from 8 to 20 characters")
    @Pattern(regexp = PASSWORD_PATTERN, message = "Passwords are only allowed alphanumeric character")
    private final String password;
}
