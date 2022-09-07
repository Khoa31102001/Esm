package com.stdio.esm.model.mapper;


import com.stdio.esm.dto.EmployeeDto;
import com.stdio.esm.dto.SkillDto;
import com.stdio.esm.dto.SkillTypesDto;
import com.stdio.esm.dto.response.SkillResponse;
import com.stdio.esm.dto.response.SkillTypesResponse;
import com.stdio.esm.model.Employee;
import com.stdio.esm.model.Skill;
import com.stdio.esm.model.SkillTypes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillTypesMapper {

    SkillTypes skillTypesDtoToSkillTypes(SkillTypesDto skillTypesDto);

    SkillTypesDto skillTypesToSkillTypesDto(SkillTypes skillTypes);

//    List<SkillTypesDto> skillTypesListToSkillTypesDtoList(List<SkillTypes> skillTypes);

    List<SkillTypesResponse> skillTypesListToSkillTypesResponseList(List<SkillTypes> skillTypes);
}
