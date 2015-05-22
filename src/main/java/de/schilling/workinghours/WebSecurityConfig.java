package de.schilling.workinghours;

import de.schilling.workinghours.user.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
            .disable()
            .httpBasic()
            .and()
            .logout()
            .and()
            .authorizeRequests()
            .antMatchers("/", "/css/*", "/fonts/*", "/js/*", "/lib/**/*", "/partials/*", "/index.html")
            .permitAll()
            .anyRequest()
            .authenticated();
    }

    @Configuration
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        private UserDetailsServiceImpl userDetailsService;
        @Autowired
        private DaoAuthenticationProvider daoAuthenticationProvider;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {

            auth.authenticationProvider(daoAuthenticationProvider);
        }


        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider() {

            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setUserDetailsService(userDetailsService);
            daoAuthenticationProvider.setPasswordEncoder(new StandardPasswordEncoder());

            return daoAuthenticationProvider;
        }
    }
}
