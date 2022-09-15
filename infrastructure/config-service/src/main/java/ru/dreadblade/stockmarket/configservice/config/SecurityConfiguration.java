package ru.dreadblade.stockmarket.configservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Value("${app.config-service.user.username}")
    private String username;

    @Value("${app.config-service.user.password}")
    private String password;

    @Value("${app.config-service.admin.username}")
    private String adminUsername;

    @Value("${app.config-service.admin.password}")
    private String adminPassword;

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withUsername(username)
                .password(password)
                .roles("USER")
                .build());

        userDetailsManager.createUser(User.withUsername(adminUsername)
                .password(adminPassword)
                .roles("ADMIN")
                .build());

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        return httpSecurity.build();
    }
}
