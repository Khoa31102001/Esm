package com.stdio.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.stdio.esm.utilities.EsmEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.Instant;

@Data
@JsonRootName(value = "role")
public class RoleDto implements Serializable {

    private static final long serialVersionUID = -1685248054816433777L;

    @NotNull(groups = {OnUpdate.class})
    @Null(groups = OnCreate.class)
    private final Long id;

    @NotNull(message = "Please select your role")
    private final EsmEnum.Role name;
    @JsonIgnore
    private final Instant createdAt;
    @JsonIgnore
    private final Instant modifyAt;
}
