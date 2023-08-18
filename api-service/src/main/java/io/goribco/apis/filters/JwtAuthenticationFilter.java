package io.goribco.apis.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.goribco.apis.advice.ErrorAdviceHelper;
import io.goribco.apis.configs.security.jwt.JwtHelper;
import io.goribco.apis.model.errrorres.RestErrorMessage;
import io.goribco.apis.repository.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
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

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        /*
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        */
        /*
        if (Arrays.asList(AppConstants.authorizedUrl).contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }//TODO: Check Properly, Then Commit
        */
        final String authHeader = request.getHeader("Authorization");
        String authToken = null;
        String userName = null;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        authToken = authHeader.substring(7);

        try {
            userName = jwtHelper.extractUsername(authToken);
        } catch (IllegalArgumentException e) {
            logger.error("An error occurred while fetching Username from Token", e);
            processDoFilterInternalException(
                    "An error occurred while fetching Username from Token", e.getMessage(), response);
        } catch (ExpiredJwtException e) {
            logger.warn("The token has expired", e);
            processDoFilterInternalException(
                    "The token has expired", e.getMessage(), response);
        } catch (SecurityException e) {
            logger.error("Authentication Failed. Username or Password not valid.");
            processDoFilterInternalException(
                    "Authentication Failed. Username or Password not valid.", e.getMessage(), response);
        }


        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

            boolean isTokenValid = tokenRepository.findByToken(authToken)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            if (jwtHelper.isTokenValid(authToken, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void processDoFilterInternalException(String errorMsg,
                                                  String exceptionMsg,
                                                  HttpServletResponse response) throws IOException {
        RestErrorMessage restErrorMessage = ErrorAdviceHelper.get2estErrorMessage(errorMsg, exceptionMsg, HttpStatus.FORBIDDEN);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), restErrorMessage);
    }
}
