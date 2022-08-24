package com.joseph.fortress.student;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    /**
     * Use the InMemoryUserDetailsManager class to implement the
     * UserDetailsService interface.
     * @return
     */

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails toddUser = User.builder()
                .username("Todd")
                .password("password")   // Without password encoded, server/app will generate exceptions
                .roles("STUDENT")   //ROLE_STUDENT
                .build();

        return new InMemoryUserDetailsManager(
                toddUser
        );
    }
}
