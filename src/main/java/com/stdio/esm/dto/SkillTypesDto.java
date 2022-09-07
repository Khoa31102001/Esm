package com.stdio.esm.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SkillTypesDto {

    private static final String NAME_PATTERN = "^[a-zA-Z\\s]+$";

    @NotNull(groups = {OnUpdate.class})
    @Null(groups = OnCreate.class)
    private Long id;


    @NotBlank(message = "Name skill is required")
    @Size(min = 6, max = 20, message = "Name length from 6 to 20 characters")
    @Pattern(regexp = NAME_PATTERN, message = "Name are only allowed to use alphanumeric characters")
    private String name;

    private String description;

}
