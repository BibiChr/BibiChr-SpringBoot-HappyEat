package ch.fhnw.webec.happyeat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/style/**").permitAll()
            .antMatchers("/image/**").permitAll()
            .antMatchers("/register").permitAll()
            .antMatchers("/login/**").permitAll()
            .anyRequest().authenticated()

            .and().formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/shopping-lists", true)
            .permitAll()

            .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)

            .and().csrf()
            .ignoringAntMatchers("/h2-console/**")
            .and().headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
