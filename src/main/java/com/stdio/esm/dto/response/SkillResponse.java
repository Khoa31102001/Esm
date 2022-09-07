package com.stdio.esm.dto.response;

import com.stdio.esm.dto.SkillDto;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class SkillResponse extends SkillDto {

    @NotNull
    private List<String> skillLevels;


    @NotNull
    private List<String> projects_skills;


    @NotNull

    private List<String> employees_skills;
}
