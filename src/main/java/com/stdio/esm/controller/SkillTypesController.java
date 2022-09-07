package com.stdio.esm.controller;


import com.stdio.esm.dto.OnCreate;
import com.stdio.esm.dto.OnUpdate;
import com.stdio.esm.dto.SkillDto;
import com.stdio.esm.dto.SkillTypesDto;
import com.stdio.esm.dto.response.EsmResponse;
import com.stdio.esm.dto.response.SkillResponse;
import com.stdio.esm.dto.response.SkillTypesResponse;
import com.stdio.esm.service.SkillService;
import com.stdio.esm.service.SkillTypesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/skill-types")
@Validated
public class SkillTypesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkillTypesController.class);

    @Autowired
    private SkillTypesService skillTypesService;

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> getAllSkillTypes() {
        LOGGER.info("Started get all skillTypes in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        Map<String, List<SkillTypesResponse>> responseData = skillTypesService.getAllListSkillTypes();
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get all skillTypes successfully!");
        esmResponse.setResponseData(responseData);
        LOGGER.info("Finished get all skillTypes in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @GetMapping(path = "/id/{skillTypesId}")
    public ResponseEntity<Map<String, Object>> getSkillTypesById( @DecimalMin(value = "1") @PathVariable(name = "skillTypesId") Long skillTypesId) {
        LOGGER.info("Started get skillTypes by skillTypes ID in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(skillTypesService.getSkillTypesById(skillTypesId));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get skillTypes by skillTypes ID successfully!");
        LOGGER.info("Finished get skillTypes by skillTypes ID in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<Map<String, Object>> getSkillTypesByName( @NotBlank @PathVariable(name = "name") String name) {
        LOGGER.info("Started get skillTypes by skillTypes name in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(skillTypesService.getSkillTypesByName(name));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get skillTypes by skillTypes name successfully!");
        LOGGER.info("Finished get skillTypes by skillTypes name in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }


    @PostMapping(path = "/add")
    @Validated(OnCreate.class)
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> addNewSkillTypes(@Valid @RequestBody SkillTypesDto skillTypesDto) {
        LOGGER.info("Started create a skillTypes in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(skillTypesService.addNewSkillTypes(skillTypesDto));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Add new skillTypes successfully!");
        LOGGER.info("Finished create a skillTypes in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }
    @PostMapping(path = "/update")
    @Validated(OnUpdate.class)
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> updateSkillTypes(@Valid @RequestBody SkillTypesDto skillTypesDto) {
        LOGGER.info("Started update a skillTypes in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(skillTypesService.updateskillTypes(skillTypesDto));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("update skillTypes successfully!");
        LOGGER.info("Finished update a skillTypes in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @DeleteMapping(path = "/delete/id/{skillTypesId}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> deleteSkillTypesById( @DecimalMin(value = "1") @PathVariable(name = "skillTypesId") Long skillTypesId) {
        LOGGER.info("Start delete skillTypes by Id in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        skillTypesService.deleteSkillTypesById(skillTypesId);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Delete skillTypes successfully!");
        LOGGER.info("Finished delete skillTypes by Id in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @DeleteMapping(path = "/delete/name/{name}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> deleteSkillTypesByUsername(@NotBlank @PathVariable(name = "name") String name) {
        LOGGER.info("Start delete skillTypes by username in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        skillTypesService.deleteSkillTypesByName(name);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Delete skillTypes successfully!");
        LOGGER.info("Finished delete skillTypes by username in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

}
