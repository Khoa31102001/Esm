package com.stdio.esm.controller;


import com.stdio.esm.dto.EmployeeDto;
import com.stdio.esm.dto.OnCreate;
import com.stdio.esm.dto.OnUpdate;
import com.stdio.esm.dto.response.EsmResponse;
import com.stdio.esm.exception.EsmException;
import com.stdio.esm.service.EmployeeService;
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
@RequestMapping(path = "/employee")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> getAllEmployee() {
        LOGGER.info("Started get all employees in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        Map<String, List<EmployeeDto>> responseData = employeeService.getAllEmployee();
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get all employees successfully!");
        esmResponse.setResponseData(responseData);
        LOGGER.info("Finished get all employees in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @GetMapping(path = "/id/{employeeId}")
    public ResponseEntity<Map<String, Object>> getEmployeeById(@DecimalMin(value = "1") @PathVariable(name = "employeeId") Long employeeId) {
        LOGGER.info("Started get a employee employee ID in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(employeeService.getEmployeeById(employeeId));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get a employee by employee ID successfully!");
        LOGGER.info("Finished get a employee employee ID in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<Map<String, Object>> getEmployeeByName(@NotBlank @PathVariable(name = "name") String name) {
        LOGGER.info("Started get an employee by name in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(employeeService.getEmployeeByName(name));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get an employee by name successfully!");
        LOGGER.info("Finished get an employee by name in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @PostMapping(path = "/add")
    @Validated(OnCreate.class)
    public ResponseEntity<Map<String, Object>> addNewEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        LOGGER.info("Started create a employee in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(employeeService.addNewEmployee(employeeDto));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Add new employee successfully!");
        LOGGER.info("Finished create a employee in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @PutMapping(path = "/update")
    @Validated(OnUpdate.class)
    public ResponseEntity<Map<String, Object>> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        LOGGER.info("Started update an employee in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(employeeService.updateEmployee(employeeDto));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Update employee successfully!");
        LOGGER.info("Finished update an employee in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @DeleteMapping(path = "/delete/id/{employeeId}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> deleteAccountById(@DecimalMin(value = "1") @PathVariable(name = "employeeId") Long employeeId) throws EsmException {
        LOGGER.info("Start delete employee by Id in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        employeeService.deleteEmployeeById(employeeId);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Delete employee successfully!");
        LOGGER.info("Finished delete employee by Id in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @DeleteMapping(path = "/delete/name/{name}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> deleteEmployeeeByName(@NotBlank @PathVariable(name = "name") String name) {
        LOGGER.info("Start delete employee by name in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        employeeService.deleteEmployeeByName(name);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Delete employee successfully!");
        LOGGER.info("Finished delete employee by name in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

}
