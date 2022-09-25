package com.joseph.fortress.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor dependency injection:
     * @param passwordEncoder
     */

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()// these specific patterns do NOT require Basic Auth
                .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name()) // protects this API
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();   // Basic Auth
    }

    /**
     * Use the InMemoryUserDetailsManager class to implement the
     * UserDetailsService interface.
     * @return
     */

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        /**
         * Here we create our STUDENT user, Todd
         */
        
        UserDetails toddUser = User.builder()
                .username("Todd")
                // Without password encoded, server/app will generate exceptions or give error message
                .password(passwordEncoder.encode("password"))
                .roles(ApplicationUserRole.STUDENT.name())   // Here we add our defined ENUM role
                .build();

        /**
         * Here we create our ADMIN user, Carlos
         */

        UserDetails carlosUser = User.builder()
                .username("Carlos")
                .password(passwordEncoder.encode("password123"))
                .roles(ApplicationUserRole.ADMIN.name())    // Here we add our defined ENUM role
                .build();

        return new InMemoryUserDetailsManager(
                toddUser,       // role of STUDENT
                carlosUser      // role of ADMIN
        );
    }
}
