package com.piseth.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    //bean permission
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //disable csrf (cross site request forgery)
        http.csrf(AbstractHttpConfigurer::disable);

        // authorize url mapping
//        http.authorizeHttpRequests(request ->{
//                request.requestMatchers("/api/v1/books/**").hasRole("CUSTOMER");
//                request.requestMatchers("/api/v1/users/**").hasRole("SYSTEM");
//                request.anyRequest().permitAll();
//        });
        http.authorizeHttpRequests(auth -> {
            //no check
            auth.requestMatchers("/api/v1/auth/**").permitAll();
            // user url allow only ADMIN
            auth.requestMatchers("/api/v1/users/**").hasAnyAuthority("SCOPE_ROLE_ADMIN", "SCOPE_ROLE_SYSTEM");
            // determine the role on method post in user url
            auth.requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAnyAuthority("SCOPE_ROLE_SYSTEM");
            // login and access no worried about the role of the user
            auth.anyRequest().authenticated();

        });

//         security mechanism
//        http.httpBasic();
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

//         make security http STATELESS
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    @Bean
    public RSAKey rsaKey(KeyPair keyPair){
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, context) -> jwkSelector.select(jwkSet);
    }

    //bean create user
    //    @Bean
    //    public InMemoryUserDetailsManager userDetailsManager(){
    //        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
    //        UserDetails admin = User.builder()
    //                .username("admin")
    //                .password(encoder.encode("1234"))
    //                .roles("ADMIN")
    //                .build();
    //        UserDetails book = User.builder()
    //                .username("book")
    //                .password(encoder.encode("2222"))
    //                .roles("BOOK")
    //                .build();
    //        UserDetails user = User.builder()
    //                .username("user")
    //                .password(encoder.encode("9999"))
    //                .roles("USER")
    //                .build();
    //        userDetailsManager.createUser(admin);
    //        userDetailsManager.createUser(book);
    //        userDetailsManager.createUser(user);
    //        return userDetailsManager;
    //    }

}
