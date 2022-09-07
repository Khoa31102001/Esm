package com.stdio.esm.controller;


import com.stdio.esm.dto.AccountDto;
import com.stdio.esm.dto.OnCreate;
import com.stdio.esm.dto.OnUpdate;
import com.stdio.esm.dto.request.ChangePasswordDto;
import com.stdio.esm.dto.response.EsmResponse;
import com.stdio.esm.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/account")
@Validated
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;

    @GetMapping(path = "")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllAccount() {
        LOGGER.info("Started get all account in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        Map<String, List<AccountDto>> responseData = accountService.getAllAccount();
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get all account successfully!");
        esmResponse.setResponseData(responseData);
        LOGGER.info("Finished get all account in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @GetMapping(path = "/id/{accountId}")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getAccountById( @DecimalMin(value = "1") @PathVariable(name = "accountId") Long accountId) {
        LOGGER.info("Started get an account account ID in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(accountService.getAccountById(accountId));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get an account by account ID successfully!");
        LOGGER.info("Finished get an account account ID in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @GetMapping(path = "/username/{username}")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getAccountByUsername( @NotBlank @PathVariable(name = "username") String username) {
        LOGGER.info("Started get an account by username in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(accountService.getAccountByUsername(username));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Get an account by username successfully!");
        LOGGER.info("Finished get an account by username in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @PostMapping(path = "/add")
    @Validated(OnCreate.class)
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> addNewAccount(@Valid @RequestBody AccountDto accountDto) {
        LOGGER.info("Started create an account in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(accountService.addNewAccount(accountDto));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Add new account successfully!");
        LOGGER.info("Finished create an account in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @PutMapping(path = "/update")
    @Validated(OnUpdate.class)
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> updateAccount(@Valid @RequestBody AccountDto accountDto) {
        LOGGER.info("Started update an account in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setResponseData(accountService.updateAccount(accountDto));
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Update account successfully!");
        LOGGER.info("Finished update an account in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @DeleteMapping(path = "/delete/id/{accountId}")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteAccountById( @DecimalMin(value = "1") @PathVariable(name = "accountId") Long accountId) {
        LOGGER.info("Start delete account by Id in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        accountService.deleteAccountById(accountId);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Delete account successfully!");
        LOGGER.info("Finished delete account by Id in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @DeleteMapping(path = "/delete/username/{username}")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteAccountByUsername(@NotBlank @PathVariable(name = "username") String username) {
        LOGGER.info("Start delete account by username in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        accountService.deleteAccountByUsername(username);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Delete account successfully!");
        LOGGER.info("Finished delete account by username in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        LOGGER.info("Start change password in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        accountService.changePassword(changePasswordDto);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("change password account successfully!");
        LOGGER.info("Finished change password in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword( @Email @RequestParam(name = "email") String email) {
        LOGGER.info("Start reset password in controller layer");
        EsmResponse esmResponse = new EsmResponse();
        accountService.resetPassword(email);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("change password account successfully!");
        LOGGER.info("Finished reset password in controller layer");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }
}
