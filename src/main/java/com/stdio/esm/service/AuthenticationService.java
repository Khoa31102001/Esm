package com.stdio.esm.service;

import com.stdio.esm.component.JwtTokenProvider;
import com.stdio.esm.dto.response.EsmToken;
import com.stdio.esm.exception.EsmException;
import com.stdio.esm.dto.response.EsmResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Map;

/**
 * @author thanghoang
 * @since 19/05/2022 - 11:11
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    /**
     * Check login from username and password
     *
     * @param username {@link String}
     * @param password {@link String}
     * @return {@link Map<String,Object>}
     */
    public EsmToken login(@NotBlank String username,@NotBlank String password) throws EsmException{
        LOGGER.info("Start login service");
        Authentication authentication = this.authenticate(username, password);
        EsmToken responseData = tokenProvider.buildTheAuthenticatedResponse(authentication);
        return responseData;
    }

    /**
     * Logout process
     *
     * @param authorization
     * @return
     */
    public EsmResponse logout(String authorization) throws EsmException {
        // TODO: Manage token and destroy it when user logout
        return null;
    }

    private Authentication authenticate( String username,String password) throws EsmException {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            return this.authenticationManager.authenticate(authenticationToken);
        } catch (DisabledException disabledException) {
            LOGGER.error(disabledException.getMessage(), disabledException);
            throw new EsmException("The account is disabled", disabledException);
        } catch (BadCredentialsException badCredentialsException) {
            LOGGER.error(badCredentialsException.getMessage(), badCredentialsException);
            throw new EsmException("Username or password is not correct", badCredentialsException);
        }
    }

    /**
     * Get new accessToken from refreshToken
     *
     * @param refreshToken
     * @return
     * @throws EsmException
     */
    public EsmToken getNewAccessTokenFromRefreshToken(@NotBlank String refreshToken) throws EsmException, BadCredentialsException {
        LOGGER.info("Get new AccessToken from RefreshToken");
        if (tokenProvider.validateToken(refreshToken)) {
            return tokenProvider.generatedAccessJwtFromRefreshJwt(refreshToken);
        }
        return null;
    }
}
