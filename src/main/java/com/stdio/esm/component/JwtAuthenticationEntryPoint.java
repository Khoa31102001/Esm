package com.stdio.esm.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stdio.esm.dto.response.EsmResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        LOGGER.error("Unauthorized error: {}", authException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Exception exception = (Exception) request.getAttribute("exception");

        String message;

        if (exception != null) {
            LOGGER.error("Error authentication: {}", exception.getMessage());
            EsmResponse esmResponse = new EsmResponse();
            esmResponse.setResponseData(exception.toString());
            esmResponse.setCode(HttpStatus.UNAUTHORIZED.value());
            esmResponse.setStatus(EsmResponse.ERROR);
            esmResponse.setMessage("ERROR UNATHORIZED");
            byte[] body = new ObjectMapper().writeValueAsBytes(esmResponse.getResponse());
            response.getOutputStream().write(body);
        } else {

            if (authException.getCause() != null) {
                message = authException.getCause().toString() + " " + authException.getMessage();
            } else {
                message = authException.getMessage();
            }
            LOGGER.error("Error authentication: {}",message);
            EsmResponse esmResponse = new EsmResponse();
            esmResponse.setResponseData(message);
            esmResponse.setCode(HttpStatus.UNAUTHORIZED.value());
            esmResponse.setStatus(EsmResponse.ERROR);
            esmResponse.setMessage("ERROR UNATHORIZED");
            byte[] body = new ObjectMapper().writeValueAsBytes(esmResponse.getResponse());
            response.getOutputStream().write(body);
        }
    }
}
