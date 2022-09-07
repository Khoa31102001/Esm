package com.stdio.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.stdio.esm.utilities.EsmEnum;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@JsonRootName(value = "employee")
public class EmployeeDto implements Serializable {

    private static final long serialVersionUID = 4751827029344554462L;
    private static final String NAME_PATTERN = "^[a-zA-Z\\s]+$";
    private static final String ADDRESS_PATTERN = "^[a-zA-Z0-9\\s]+$";
    private static final String PHONE_PATTERN = "(^$|[0-9]{10})";


    @NotNull(groups = {OnUpdate.class})
    @Null(groups = OnCreate.class)
    private final Long id;

    @NotBlank(message = "{message.login.name.required}")
    @Size(min = 6, max = 20, message = "Name length from 6 to 20 characters")
    @Pattern(regexp = NAME_PATTERN, message = "Name are only allowed to use alphanumeric characters")
    private final String name;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Kolkata")
    private final Instant dateOfBirth;

    @NotBlank(message = "{message.login.address.required}")
    @Size(min = 6, max = 20, message = "Address length from 6 to 20 characters")
    @Pattern(regexp = ADDRESS_PATTERN, message = "Address are only allowed to use alphanumeric characters")
    private final String address;
    private final String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Kolkata")
    private final Instant startDate;

    @JsonIgnore
    private final Instant createdAt;

    @JsonIgnore
    private final Instant modifyAt;

    private final String jobTitle;
    @Email(message = "Email should be valid")
    private final String email;

    @Pattern(regexp = PHONE_PATTERN, message = "Phone number must contain 10 numbers")
    private final String phoneNumber;

    @Size(min = 10, max = 255, message = "Place of birth must be between 10 and 255 characters")
    private final String placeOfBirth;

    @Size(min = 10, max = 50, message = "Nationality must be between 10 and 50 characters")
    private final String nationality;

    @NotNull(message = "Please select your gender")
    private final EsmEnum.Gender gender;
    private final String website;
    private final String professionalSummary;
}
