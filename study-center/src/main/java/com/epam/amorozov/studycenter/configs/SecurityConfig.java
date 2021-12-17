package com.epam.amorozov.studycenter.configs;

import com.epam.amorozov.studycenter.services.implementations.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.epam.amorozov.studycenter.models.enums.Roles.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v*/courses/all").permitAll()
                .antMatchers("/api/v*/courses/{id}").authenticated()
                .antMatchers("/api/v*/courses/add").hasRole(String.valueOf(TEACHER))
                .antMatchers("/api/v*/payment").authenticated()
                .antMatchers("/api/v*/students/{id}").hasRole(String.valueOf(TEACHER))
                .antMatchers("/api/v*/students/all").hasRole(String.valueOf(TEACHER))
                .antMatchers("/api/v*/students/{studentId}, {courseId}").hasRole(String.valueOf(TEACHER))
                .antMatchers("/api/v*/students/rate/{studentId},{newScore},{topicId}").hasRole(String.valueOf(TEACHER))
                .antMatchers("/api/v*/students/deduct/{id}").authenticated()
                .antMatchers("/api/v*/sorts/days_to_end").hasRole(String.valueOf(TEACHER))
                .antMatchers("/api/v*/sorts/by_avg_scores").hasRole(String.valueOf(TEACHER))
                .antMatchers("/api/v*/sorts/not_be_deduct").hasRole(String.valueOf(TEACHER))
                .antMatchers("/h2-console/add_on_course/{studentId}, {courseId}").authenticated()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }
}
