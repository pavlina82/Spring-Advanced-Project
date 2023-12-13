package com.EShop.EShop.config;

import com.EShop.EShop.oauth.OAuthSuccessHandler;
import com.EShop.EShop.repository.UserRepository;
import com.EShop.EShop.service.impl.ShopUserDetailsService;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, OAuthSuccessHandler oAuthSuccessHandler) throws Exception {
        httpSecurity.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                                .requestMatchers("/css/**", "/js/**", "/products/fetch/sale", "/fetch/sales/All").permitAll()
                                .requestMatchers("/", "/login", "/register").permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(formLogin -> {
                    formLogin.loginPage("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/home", true)
                            .failureForwardUrl("/unauthorized");
                }).logout(logout -> {
                    logout.logoutUrl("/login?logout").permitAll()
                            .invalidateHttpSession(true);
                }).oauth2Login(oauth ->
                        oauth.successHandler(oAuthSuccessHandler))
                .build();


        return null;
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new ShopUserDetailsService(userRepository);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }


}
