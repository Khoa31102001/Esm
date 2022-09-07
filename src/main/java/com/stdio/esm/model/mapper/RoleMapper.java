package com.stdio.esm.model.mapper;

import com.stdio.esm.dto.RoleDto;
import com.stdio.esm.model.Employee;
import com.stdio.esm.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role roleDtoToRole(RoleDto roleDto);


    RoleDto roleToRoleDto(Role role);

    List<RoleDto> roleListToRoleDtoList(List<Role> roles);
}
