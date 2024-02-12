package org.example.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Hey spring securiy allow all the requests dont block
        http
                .authorizeRequests(
                        authorize -> {
                            try {
                                authorize
                                .anyRequest().permitAll()
                                .and().cors().disable().
                                csrf().disable();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
        return http.build();
    }

}
