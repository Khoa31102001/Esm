package com.stdio.esm.dto.response;

import com.stdio.esm.dto.SkillDto;
import com.stdio.esm.dto.SkillTypesDto;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkillTypesResponse extends SkillTypesDto {
    @NotNull
    private List<SkillDto> skills;
}
