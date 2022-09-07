package com.stdio.esm.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ChangePasswordDto implements Serializable {

    private static final long serialVersionUID = -7055489393649396249L;
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]+$";


    @NotBlank(message = "Old password is required")
    @Size(min = 6, max = 20, message = "Old password length from 6 to 20 characters")
    @Pattern(regexp = PASSWORD_PATTERN, message = "Old password are only allowed alphanumeric character")
    private final String oldPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 6, max = 20, message = "New password length from 6 to 20 characters")
    @Pattern(regexp = PASSWORD_PATTERN, message = "New password are only allowed alphanumeric character")
    private final String newPassword;

    @NotBlank(message = "Confirm new password is required")
    @Size(min = 6, max = 20, message = "Confirm new password length from 6 to 20 characters")
    @Pattern(regexp = PASSWORD_PATTERN, message = "Confirm new password are only allowed alphanumeric character")
    private final String confirmNewPassword;
}
