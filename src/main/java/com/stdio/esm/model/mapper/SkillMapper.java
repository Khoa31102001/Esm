package com.stdio.esm.model.mapper;


import com.stdio.esm.dto.SkillDto;
import com.stdio.esm.dto.response.SkillResponse;
import com.stdio.esm.model.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",uses = {SkillTypesMapper.class})
public interface SkillMapper {
    @Mappings({@Mapping(target = "skillLevels", source = "skillLevels"), @Mapping(target = "projects_skills", source = "projects_skills"), @Mapping(target = "employees_skills", source = "employees_skills")})
    SkillResponse skillToSkillResponse(Skill skill, List<String> skillLevels, List<String> projects_skills, List<String> employees_skills);

    Skill skillDtoToSkill(SkillDto skillDto);

    SkillDto skillToSkillDto(Skill skill);
}
