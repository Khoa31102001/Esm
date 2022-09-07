package com.stdio.esm.service;

import com.stdio.esm.dto.RoleDto;
import com.stdio.esm.exception.RoleNotFound;
import com.stdio.esm.model.Role;
import com.stdio.esm.model.mapper.RoleMapper;
import com.stdio.esm.repository.RoleRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

    /**
     * Get all list role
     *
     * @return {@link List<RoleDto>>}
     */
    @Transactional(readOnly = true)
    public Map<String, List<RoleDto>> getAllRole() {
        LOGGER.info("Started get all role in service layer");
        Map<String, List<RoleDto>> responseData = new HashMap<>();
        responseData.put("accounts", roleMapper.roleListToRoleDtoList(roleRepository.findAll()));
        LOGGER.info("Finished get all role in service layer");
        return responseData;
    }

    /**
     * Get role from id
     *
     * @param roleId
     * @return {@link RoleDto}
     */
    @Transactional(readOnly = true)
    public RoleDto getRoleById(@NotNull Long roleId) throws RoleNotFound {
        LOGGER.info("Started get a role by id in service layer");
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFound("Role is not existed"));
        LOGGER.info("Finished get a role by id in service layer");
        return roleMapper.roleToRoleDto(role);
    }

    /**
     * Get role from name
     *
     * @param name
     * @return {@link RoleDto}
     */
    @Transactional(readOnly = true)
    public RoleDto getRoleByName(@NotBlank String name) throws RoleNotFound {
        LOGGER.info("Started get a role by name in service layer");
        Role role = roleRepository.findByName(name).orElseThrow(() -> new RoleNotFound("Role is not existed"));
        LOGGER.info("Finished get a role by name in service layer");
        return roleMapper.roleToRoleDto(role);
    }

    /**
     * Delete role by id
     *
     * @param roleId
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteRoleById(@NotNull Long roleId) throws RoleNotFound {
        LOGGER.info("Started delete a role in service layer");
        roleRepository.findById(roleId).ifPresentOrElse((role) -> {
            roleRepository.delete(role);
        }, () -> {
            throw new RoleNotFound("Role is not existed");
        });
        LOGGER.info("Finished delete a role in service layer");
    }

    /**
     * Delete role by name
     *
     * @param name
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void deleteRoleByName(@NotBlank String name) throws RoleNotFound {
        LOGGER.info("Started delete a Role in service layer");
        roleRepository.findByName(name).ifPresentOrElse((role) -> {
            roleRepository.delete(role);
        }, () -> {
            throw new RoleNotFound("Role is not existed");
        });
        LOGGER.info("Finished delete a Role in service layer");
    }
}
