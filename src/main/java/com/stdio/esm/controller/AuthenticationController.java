package com.stdio.esm.controller;


import com.stdio.esm.dto.request.LoginDto;
import com.stdio.esm.dto.response.EsmResponse;
import com.stdio.esm.dto.response.EsmToken;
import com.stdio.esm.exception.EsmException;
import com.stdio.esm.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@RestController
@RequestMapping(path = "/authentication")
@Validated
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);


    /**
     * Login with username and password
     * @param loginDto
     * @return Information of login processing
     * @author Thang Hoang
     */
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String,Object>> login(@Valid @RequestBody LoginDto loginDto) throws EsmException {
        LOGGER.info("Start login authentication controller");
        EsmResponse esmResponse = new EsmResponse();
        EsmToken responseData = authenticationService.login(loginDto.getUsername(), loginDto.getPassword());
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Login successfully!");
        esmResponse.setResponseData(responseData);
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Map<String,Object>> logout(@RequestHeader (name="Authorization") String authorization) throws EsmException {
        LOGGER.info("Start logout authentication controller");
        EsmResponse esmResponse = authenticationService.logout(authorization);
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Login successfully!");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    /**
     * Get new accessToken from refreshToken
     * @param request
     * @return
     * @throws EsmException
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String,Object>> refreshToken(HttpServletRequest request) throws EsmException, BadCredentialsException {
        LOGGER.info("Start refresh token controller");
        String refreshToken = request.getHeader("refreshToken");
        EsmToken responseData = authenticationService.getNewAccessTokenFromRefreshToken(refreshToken);
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setCode(HttpStatus.OK.value());
        esmResponse.setStatus(EsmResponse.SUCCESS);
        esmResponse.setMessage("Refresh Token successfully!");
        esmResponse.setResponseData(responseData);
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }
}
