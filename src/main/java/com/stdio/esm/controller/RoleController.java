package com.stdio.esm.controller;

import com.stdio.esm.dto.RoleDto;
import com.stdio.esm.dto.response.EsmResponse;
import com.stdio.esm.exception.EsmException;
import com.stdio.esm.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/role")
@Validated
public class RoleController {
    @Autowired
    private RoleService roleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> getAllRole() {
        LOGGER.info("Started get all roles in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        Map<String, List<RoleDto>> responseData = roleService.getAllRole();
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get all roles successfully!");
        esmResponse.setResponseData(responseData);
        LOGGER.info("Finished get all roles in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @GetMapping(path = "/id/{roleId}")
    public ResponseEntity<Map<String, Object>> getRoleById(@DecimalMin(value = "1") @PathVariable(name = "roleId") Long roleId) {
        LOGGER.info("Started get a role role ID in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(roleService.getRoleById(roleId));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get a role by role ID successfully!");
        LOGGER.info("Finished get a role role ID in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<Map<String, Object>> getRoleByName(@NotBlank @PathVariable(name = "name") String name) {
        LOGGER.info("Started get a role by name in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(roleService.getRoleByName(name));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get a role by name successfully!");
        LOGGER.info("Finished get a role by name in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }


    @DeleteMapping(path = "/delete/id/{employeeId}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteRoletById(@DecimalMin(value = "1") @PathVariable(name = "roleId") Long roleId) throws EsmException {
        LOGGER.info("Start delete role by Id in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        roleService.deleteRoleById(roleId);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Delete role successfully!");
        LOGGER.info("Finished delete role by Id in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @DeleteMapping(path = "/delete/name/{name}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteRoleByName(@NotBlank @PathVariable(name = "name") String name) {
        LOGGER.info("Start delete role by name in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        roleService.deleteRoleByName(name);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Delete role successfully!");
        LOGGER.info("Finished delete role by name in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

}
