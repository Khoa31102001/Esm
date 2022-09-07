package com.stdio.esm.component;

import com.stdio.esm.exception.EsmException;
import com.stdio.esm.service.AccountDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AccountDetailsService accountDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("Start filter request");
        String token = tokenProvider.getToken(request);
        try {
            if (token != null && tokenProvider.validateToken(token)) {
                String username = tokenProvider.getUsername(token);

                User user = (User) accountDetailsService.loadUserByUsername(username);
                if (tokenProvider.verifyExpirationAccessJwt(token) && user.getPassword() != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (EsmException exception) {
            String refreshToken = request.getHeader("refreshToken");
            String requestURL = request.getRequestURL().toString();
            if (refreshToken != null && requestURL.contains("refresh-token")) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        null, null, null);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                request.setAttribute("exception", exception);
            }

        } catch (BadCredentialsException exception){
            request.setAttribute("exception", exception);
        }
        filterChain.doFilter(request, response);
    }
}
