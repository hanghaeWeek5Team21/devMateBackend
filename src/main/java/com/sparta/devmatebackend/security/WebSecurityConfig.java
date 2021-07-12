package com.sparta.devmatebackend.security;

import com.sparta.devmatebackend.config.DomainConfig;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

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
        
        // @Depricated
        // TODO : 깃헙 readme 에 남기고 삭제하기 (security cors 설정법)
//        http.cors().configurationSource(request -> {
//                var cors = new CorsConfiguration();
//                cors.setAllowedOrigins(Arrays.asList(domainConfig.getFullName()));
//                cors.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH", "OPTIONS"));
//                cors.setAllowedHeaders(Collections.singletonList("*"));
//                cors.setAllowCredentials(true);
//                return cors;
//        });

        http.authorizeRequests()
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/**").permitAll() // api테스트를 위해 열어두었습니다 배포시 주석처리해주세요
                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(domainConfig.getFullName()+ "/user/login")
                .loginProcessingUrl("/api/user/login")
                .defaultSuccessUrl(domainConfig.getFullName())
                .failureUrl(domainConfig.getFullName() + "/user/login/error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl(domainConfig.getFullName()+"/user/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/user/forbidden");
    }
}