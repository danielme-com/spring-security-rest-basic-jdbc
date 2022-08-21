package com.danielme.springsecuritybasic;

import com.danielme.springsecuritybasic.controllers.CountryRestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
class SpringSecurityConfig {

   enum UserRol {
        ADMIN, USER;
    }

    @Bean
    UserDetailsService jdbcUserDetailsManager(DataSource dataSource,
                                              @Value("${security-jdbc.user}") String usersByUsernameQuery,
                                              @Value("${security.jdbc-authorities}") String authoritiesByUsernameQuery) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(authoritiesByUsernameQuery);
        return jdbcUserDetailsManager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, CountryRestController.COUNTRIES_RESOURCE+ "/*").authenticated()
                .antMatchers(HttpMethod.DELETE, CountryRestController.COUNTRIES_RESOURCE + "/*").hasRole(UserRol.ADMIN.name())
                .antMatchers("/**").permitAll()
                .and().httpBasic();
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
