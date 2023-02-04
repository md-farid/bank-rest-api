package com.teamwebsoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        /**
         * Below the custom security configuration
         */

        http.authorizeHttpRequests()
                .requestMatchers("/myAccount","/myBalance","/myCards","/myLoans").authenticated()
                .requestMatchers("/notices","/contact").permitAll()
                .and().formLogin()
                .and().httpBasic();
        return http.build();
    }

    /**
    @Bean
    InMemoryUserDetailsManager userDetailsService(){
        /*Approach 1 where we use withDefaultPasswordEncoder() method
        while creating the user details*/

        /**
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("12345")
                .authorities("admin")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .authorities("read")
                .build();

        return new InMemoryUserDetailsManager(admin,user);*/

        /*Approach 2 where we use NoOpPasswordEncoder Bean
        while creating the user details

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
        UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
        inMemoryUserDetailsManager.createUser(user);
        inMemoryUserDetailsManager.createUser(admin);
        return inMemoryUserDetailsManager;
    }*/

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
