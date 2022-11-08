package ru.dreadblade.stockmarket.discoveryservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Value("${app.eureka.service.username}")
    private String serviceUsername;

    @Value("${app.eureka.service.password}")
    private String servicePassword;

    @Value("${app.eureka.admin.username}")
    private String adminUsername;

    @Value("${app.eureka.admin.password}")
    private String adminPassword;

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        userDetailsManager.createUser(User.withUsername(serviceUsername)
                .password(servicePassword)
                .roles("SERVICE")
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
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").hasRole("ADMIN")
                .antMatchers("/eureka/**").authenticated()
                .and()
                .httpBasic();

        return httpSecurity.build();
    }
}
