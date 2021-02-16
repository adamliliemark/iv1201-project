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

/**
 * Handles configurations concerning authentication and verification of users using the Recruitment Application.
 */
@Configuration
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    /**
     *Allows a user with a given email to authenticate that it has access to the pages it attempts to enter.
     *
     * @param auth is th object used for in memory authentication
     * @throws Exception if there has been some issues with the authentication process.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select email, password, enabled "
                + "from users "
                + "where upper(email) = upper(?) and password is not null")
                .authoritiesByUsernameQuery("select user_email, authority "
                + "from authorities "
                + "where user_email = ?");
    }

    /**
     * Sets the configuration for what type of user has access to specific application views.
     * @param http allows for configuring web based security for specific http requests.
     * @throws Exception if there has been some issues concerning the configuration.
     */
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/login*").permitAll()
            .antMatchers("/restore").permitAll()
            .antMatchers("/test").permitAll()
            .antMatchers("/css/*").permitAll()
            .antMatchers("/admin*").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            //.failureHandler(authenticationFailureHandler())
            .and()
            .logout()
            .permitAll()
            .logoutUrl("/logout")
            .deleteCookies("JSESSIONID");
            //.logoutSuccessHandler(logoutSuccessHandler());
    }

    /**
     * called when the password a user has registered for its login credentials is going to be encrypted.
     * @return is the object used for encrypting the password.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


