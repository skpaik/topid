package io.goribco.apis.config;

import io.goribco.apis.configs.AppConstants;
import io.goribco.apis.configs.security.permission.PermissionApi;
import io.goribco.apis.configs.security.permission.RoleData;
import io.goribco.apis.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable).httpBasic(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(AppConstants.unAuthorizedUrls).permitAll());

        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**").authenticated());

        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/v1/management/**").hasAnyRole(RoleData.ADMIN.name(), RoleData.MANAGER.name()));
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "/api/v1/management/**").hasAnyAuthority(PermissionApi.ADMIN_READ.name(), PermissionApi.MANAGER_READ.name()).requestMatchers(HttpMethod.POST, "/api/v1/management/**").hasAnyAuthority(PermissionApi.ADMIN_CREATE.name(), PermissionApi.MANAGER_CREATE.name()).requestMatchers(HttpMethod.PUT, "/api/v1/management/**").hasAnyAuthority(PermissionApi.ADMIN_UPDATE.name(), PermissionApi.MANAGER_UPDATE.name()).requestMatchers(HttpMethod.DELETE, "/api/v1/management/**").hasAnyAuthority(PermissionApi.ADMIN_DELETE.name(), PermissionApi.MANAGER_DELETE.name())

                         /*
                .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())

                 .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                 .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                 .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                 .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
                 */);
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        http.sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authenticationProvider(authenticationProvider);

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout(logoutCustomizer -> logoutCustomizer.logoutUrl("/api/v1/auth/logout").addLogoutHandler(logoutHandler).logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));

        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(CorsConfiguration.ALL));
        configuration.setAllowedMethods(List.of(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        configuration.setAllowCredentials(true);

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.addAllowedMethod(CorsConfiguration.ALL);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        source.registerCorsConfiguration("/graphql/**", configuration);

        return source;
    }

    @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {
        return ServerCodecConfigurer.create();
    }

}
