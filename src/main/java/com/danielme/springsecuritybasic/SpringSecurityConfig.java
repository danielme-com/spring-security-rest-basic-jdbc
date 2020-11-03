package com.danielme.springsecuritybasic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private final String usersByUsernameQuery;

    private final String authoritiesByUsernameQuery;

    public SpringSecurityConfig(DataSource dataSource,
                                @Value("${security-jdbc.user}") String usersByUsernameQuery,
                                @Value("${security.jdbc-authorities}") String authoritiesByUsernameQuery) {
        this.dataSource = dataSource;
        this.usersByUsernameQuery = usersByUsernameQuery;
        this.authoritiesByUsernameQuery = authoritiesByUsernameQuery;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(usersByUsernameQuery)
                .authoritiesByUsernameQuery(authoritiesByUsernameQuery)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/country/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/country/**").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .and().httpBasic();
    }
    
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("admin").password(encoder.encode("admin"))
                    .roles("ADMIN").and()
                .withUser("user").password(encoder.encode("user"))
                    .roles("USER");
    }*/

}
