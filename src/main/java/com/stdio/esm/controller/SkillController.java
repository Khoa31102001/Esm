package com.stdio.esm.controller;


import com.stdio.esm.dto.OnCreate;
import com.stdio.esm.dto.OnUpdate;
import com.stdio.esm.dto.SkillDto;
import com.stdio.esm.dto.response.EsmResponse;
import com.stdio.esm.dto.response.SkillResponse;
import com.stdio.esm.service.SkillService;
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
@RequestMapping(path = "/skill")
@Validated
public class SkillController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkillController.class);

    @Autowired
    private SkillService skillService;

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> getAllSkill() {
        LOGGER.info("Started get all skill in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        Map<String, List<SkillResponse>> responseData = skillService.getAllListSkill();
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get all skill successfully!");
        esmResponse.setResponseData(responseData);
        LOGGER.info("Finished get all skill in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @GetMapping(path = "/id/{skillId}")
    public ResponseEntity<Map<String, Object>> getSkillById( @DecimalMin(value = "1") @PathVariable(name = "skillId") Long skillId) {
        LOGGER.info("Started get skill by skill ID in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(skillService.getSkillById(skillId));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get skill by skill ID successfully!");
        LOGGER.info("Finished get skill by skill ID in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<Map<String, Object>> getSkillByName( @NotBlank @PathVariable(name = "name") String name) {
        LOGGER.info("Started get skill by skill name in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(skillService.getSkillByName(name));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get skill by skill name successfully!");
        LOGGER.info("Finished get skill by skill name in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }


    @PostMapping(path = "/add")
    @Validated(OnCreate.class)
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> addNewSkill(@Valid @RequestBody SkillDto skillDto) {
        LOGGER.info("Started create a skill in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(skillService.addNewSkill(skillDto));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Add new skill successfully!");
        LOGGER.info("Finished create a skill in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }
    @PostMapping(path = "/update")
    @Validated(OnUpdate.class)
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> updateSkill(@Valid @RequestBody SkillDto skillDto) {
        LOGGER.info("Started update a skill in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(skillService.updateSkill(skillDto));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("update skill successfully!");
        LOGGER.info("Finished update a skill in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @DeleteMapping(path = "/delete/id/{skillId}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> deleteSkillById( @DecimalMin(value = "1") @PathVariable(name = "skillId") Long skillId) {
        LOGGER.info("Start delete skill by Id in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        skillService.deleteSkillById(skillId);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Delete skill successfully!");
        LOGGER.info("Finished delete skill by Id in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @DeleteMapping(path = "/delete/name/{name}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> deleteSkillByName(@NotBlank @PathVariable(name = "name") String name) {
        LOGGER.info("Start delete skill by username in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        skillService.deleteSkillByName(name);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Delete skill successfully!");
        LOGGER.info("Finished delete skill by username in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

}
