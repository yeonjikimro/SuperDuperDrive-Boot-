package com.udacity.jwdnd.course1.cloudstorage.config;


import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링에게 이 클래스는 환경구성 설정 파일임을 알려줌
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    public void configure( WebSecurity web ) {
        web.ignoring().antMatchers("/css/**", "/fonts/**", "/images/**", "/js/**", "/modules/**")
                .antMatchers("/h2-console/**", "/swagger-ui/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/signup","/h2-console/**", "/css/**", "/js/**").permitAll()
                .anyRequest()
                .authenticated()
                        .and()
                                .logout()
                                        .invalidateHttpSession(true)
                                                .clearAuthentication(true)
                                                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                                                .invalidateHttpSession(true)
                                                                        .deleteCookies("JSESSIONID")
                                                                                .logoutSuccessUrl("/login?logout");

        http.formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/home", true);
    }


}


