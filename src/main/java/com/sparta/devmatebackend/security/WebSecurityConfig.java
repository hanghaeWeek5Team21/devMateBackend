package com.sparta.devmatebackend.security;

import com.sparta.devmatebackend.config.DomainConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DomainConfig domainConfig;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        //권한
        http.authorizeRequests()
                .antMatchers("/images/**", "/css/**", "/user/**", "/h2-console/**", "/api/**").permitAll()
                .anyRequest().authenticated();

        //로그인
        http.formLogin()
                .loginProcessingUrl("/api/user/login")
                .defaultSuccessUrl(domainConfig.getFullName()+"/")
                .failureUrl(domainConfig.getFullName() + "/login/?res=false");

        //로그아웃
        http.logout()
                .logoutUrl(domainConfig.getFullName()+"/redirect/logout");

        //권한이 없는 경우
        http.exceptionHandling()
                .accessDeniedPage("/user/forbidden");
    }
}