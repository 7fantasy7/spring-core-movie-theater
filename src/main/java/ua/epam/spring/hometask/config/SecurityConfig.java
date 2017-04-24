package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import ua.epam.spring.hometask.service.security.CustomUserDetailsService;

import javax.sql.DataSource;


@Configuration
@ComponentScan({"ua.epam.spring.hometask", "ua.epam.spring.hometask.service.security"})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/index", "/booking/test-user-acc/**").permitAll()
                .antMatchers("/ticket").hasRole("BOOKING_MANAGER")
                .antMatchers("/user", "/auditorium", "/event", "/upload").hasAnyRole("REGISTERED_USER", "BOOKING_MANAGER")
                .antMatchers("/**").hasAnyRole("REGISTERED_USER", "BOOKING_MANAGER")
                .anyRequest().authenticated().and()

                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .loginProcessingUrl("/j_spring_security_check")
                .successForwardUrl("/index")
                //.successHandler(savedRequestAwareAuthenticationSuccessHandler())
                .failureUrl("/login?message=wrong password or login")
                .usernameParameter("j_username")
                .passwordParameter("j_password").and()

                .exceptionHandling().and()
                .csrf().and()
                .rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository()).key("$Ksd71AKD)($IJO4dD9aADfK#!").tokenValiditySeconds(1209600).and()

                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(false).deleteCookies("JSESSIONID", "remember-me");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
        auth.setTargetUrlParameter("/index");
        return auth;
    }

    // SaltSource doesn't work with new PasswordEncoder interface

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,
//                                                               SaltSource saltSource,
                                                               PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        daoAuthenticationProvider.setSaltSource(saltSource);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

//    @Bean
//    public SaltSource saltSource() {
//        ReflectionSaltSource saltSource = new ReflectionSaltSource();
//        saltSource.setUserPropertyToUse("getEmail");
//        return saltSource;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

}
