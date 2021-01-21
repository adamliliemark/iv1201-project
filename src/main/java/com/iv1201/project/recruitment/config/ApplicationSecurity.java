package com.iv1201.project.recruitment.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception {
        System.err.println(passwordEncoder().encode("pass"));
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select email, password, enabled "
                + "from users "
                + "where email = ?")
                .authoritiesByUsernameQuery("select user_email, authority "
                + "from authorities "
                + "where user_email = ?");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login*").permitAll()
            .antMatchers("/test").permitAll()
            .antMatchers("/admin*").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            //.failureHandler(authenticationFailureHandler())
            .and()
            .logout()
            .permitAll();
            //.logoutUrl("/perform_logout")
            //.deleteCookies("JSESSIONID");
            //.logoutSuccessHandler(logoutSuccessHandler());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


