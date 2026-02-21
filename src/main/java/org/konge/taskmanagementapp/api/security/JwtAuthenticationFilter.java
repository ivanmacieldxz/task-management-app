package org.konge.taskmanagementapp.api.security;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.service.jwt.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest httpServletRequest,
            @Nonnull HttpServletResponse httpServletResponse,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = httpServletRequest.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        //if authHeader is not valid, pass to the next filter (and eventually reject)
        if (!authHeaderIsValid(authHeader)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        jwt = getTokenFromAuthHeader(authHeader);
        userEmail = jwtService.extractUsername(jwt);

        //if user could be retrieved from token and current request is not authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            //and has a valid token, build their identification in the system
            if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );

                //and load it to the request context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private boolean authHeaderIsValid(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    private String getTokenFromAuthHeader(String authHeader) {
        return authHeader.substring(7);
    }

}
