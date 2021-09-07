package com.sparta.devmatebackend.security;

import com.sparta.devmatebackend.config.DomainConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final DomainConfig domainConfig;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        //로그인
        http.formLogin()
                .loginProcessingUrl("/api/user/login")
                .defaultSuccessUrl(domainConfig.getFullName()+"/")
                .failureUrl(domainConfig.getFullName() + "/login/?res=false");

        //로그아웃
        http.logout()
                .logoutUrl(domainConfig.getFullName()+"/redirect/logout");

        // TODO : Functional Security 권한 방식인

        //권한 열림
        http.authorizeRequests()
                .antMatchers("/images/**", "/css/**", "/user/**", "/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/user", "/api/user/{id}", "/api/user/username", "/api/comment", "/api/file/image/{filename}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user", "/api/file/image").permitAll();

        //권한 필요
        http.authorizeRequests()
                .anyRequest().authenticated();

        //권한 없음
        http.exceptionHandling()
                .accessDeniedPage("/user/forbidden");
    }
}