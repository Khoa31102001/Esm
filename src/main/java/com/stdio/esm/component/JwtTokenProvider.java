package com.stdio.esm.component;

import com.stdio.esm.dto.response.EsmToken;
import com.stdio.esm.exception.EsmException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */

@Component
public class JwtTokenProvider implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Value("${application.security.authorization-header}")
    private String AUTHORIZATION_HEADER;

    @Value("${application.security.token-prefix}")
    private String TOKEN_PREFIX;

    @Value("${application.security.password-secret}")
    private String SECRET;
    @Value("${application.security.duration-accessToken}")
    private Long DURATION_ACCESSTOKEN;

    @Value("${application.security.duration-refreshToken}")
    private Long DURATION_REFRESHTOKEN;


    /**
     * Generate access token from EsmUserDetail information
     *
     * @param userDetails {@link UserDetails}
     * @return {@link String}
     */
    public String generatedAccessJwt(UserDetails userDetails) {
        Date date = new Date();
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + DURATION_ACCESSTOKEN))
                .signWith(SignatureAlgorithm.HS512, encodeSecret)
                .compact();
    }

    /**
     * Generate refresh token from EsmUserDetail information
     *
     * @param userDetails {@link UserDetails}
     * @return {@link String}
     */
    public String generatedRefreshJwt(UserDetails userDetails) {
        Date date = new Date();
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + DURATION_REFRESHTOKEN))
                .signWith(SignatureAlgorithm.HS512, encodeSecret)
                .compact();
    }

    /**
     * Check the expiration date of the access token
     *
     * @param accessToken {@link UserDetails}
     * @return {@link Boolean}
     */

    public boolean verifyExpirationAccessJwt(String accessToken) {
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        Date expiredDate = Jwts.parser().setSigningKey(encodeSecret).parseClaimsJws(accessToken).getBody().getExpiration();
        if (expiredDate.compareTo(Date.from(Instant.now())) < 0) {
            return false;
        }
        return true;
    }

    /**
     * Generate new access token from refresh token when the access token has expired
     *
     * @param refreshToken {@link String}
     * @return {@link String}
     */
    public EsmToken generatedAccessJwtFromRefreshJwt(String refreshToken) {
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        Claims encodeInformation = Jwts.parser().setSigningKey(encodeSecret).parseClaimsJws(refreshToken).getBody();
        Date date = new Date();
        String token = Jwts.builder()
                .setSubject(encodeInformation.getSubject())
                .claim("roles", encodeInformation.get("roles"))
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + DURATION_ACCESSTOKEN))
                .signWith(SignatureAlgorithm.HS512, encodeSecret)
                .compact();
        EsmToken response = new EsmToken(encodeInformation.getSubject(),token, refreshToken,
                new Date(), java.sql.Date.from(Instant.now().plusMillis(DURATION_ACCESSTOKEN)));
        return response;
    }

    /**
     * Get username of user from user's token
     *
     * @param token {@link String}
     * @return {@link String}
     */
    public String getUsername(String token) {
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        return Jwts.parser()
                .setSigningKey(encodeSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * validate token
     *
     * @param token {@link String}
     * @return {@link Boolean}
     */
    public boolean validateToken(String token) throws EsmException, BadCredentialsException {
        try {
            String encodeSecret = TextCodec.BASE64.encode(SECRET);
            Jwts.parser().setSigningKey(encodeSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException exception) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", exception);

        } catch (ExpiredJwtException expiredJwtException) {
            throw new EsmException("Token expired", expiredJwtException);
        }
    }

    /**
     * Get token from header <b>Authorization</b> of request
     *
     * @param request {@link HttpServletRequest}
     * @return {@link String}
     */
    public String getToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX + " ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    /**
     * buildTheAuthenticatedResponse
     *
     * @param authentication
     * @return
     * @author ThangHoang
     */
    public EsmToken buildTheAuthenticatedResponse(Authentication authentication) {
        EsmToken response = new EsmToken(authentication.getName(), this.generateAccessToken(authentication), this.generateRefreshToken(authentication),
                new Date(), java.sql.Date.from(Instant.now().plusMillis(DURATION_ACCESSTOKEN)));
        return response;
    }

    /**
     * generateRefreshToken
     *
     * @param authentication
     * @return
     * @author ThangHoang
     */
    private String generateRefreshToken(Authentication authentication) {
        Date date = new Date();
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + DURATION_REFRESHTOKEN))
                .signWith(SignatureAlgorithm.HS512, encodeSecret)
                .compact();
    }

    /**
     * generateAccessToken
     *
     * @param authentication
     * @return
     * @author ThangHoang
     */
    private String generateAccessToken(Authentication authentication) {
        Date date = new Date();
        String encodeSecret = TextCodec.BASE64.encode(SECRET);
        LOGGER.info(authentication.getName());
        LOGGER.info(authentication.getPrincipal().toString());
        LOGGER.info(authentication.toString());
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("roles", authentication.getAuthorities())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + DURATION_ACCESSTOKEN))
                .signWith(SignatureAlgorithm.HS512, encodeSecret)
                .compact();
    }
}
