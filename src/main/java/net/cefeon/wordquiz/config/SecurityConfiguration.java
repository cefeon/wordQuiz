package net.cefeon.wordquiz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user/*/*/*").hasAuthority("ADMIN")
                .antMatchers("/user/wordlist").hasAuthority("ADMIN")
                .antMatchers("/user/wordlist/**").hasAuthority("ADMIN")
                .antMatchers("/review/add/**").hasAuthority("ADMIN")
                .antMatchers("/translations/**").hasAuthority("ADMIN")
                .antMatchers("/loadcsv").hasAuthority("ADMIN")
                .antMatchers("/pl/en/**").permitAll()
                .antMatchers("/en/pl/**").permitAll()
                .antMatchers("/compare/*/*/*/*").permitAll()
                .antMatchers("/**").denyAll()
                .and().httpBasic()
                .and().formLogin();
        http.csrf().disable();
    }

}