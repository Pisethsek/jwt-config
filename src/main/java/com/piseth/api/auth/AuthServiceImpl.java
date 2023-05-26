package com.piseth.api.auth;

import com.piseth.api.auth.web.AuthDto;
import com.piseth.api.auth.web.LogInDto;
import com.piseth.api.auth.web.RegisterDto;
import com.piseth.api.user.User;
import com.piseth.api.user.UserMapStruct;
import com.piseth.util.MailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{

    private final AuthRepository authRepository;
    private final UserMapStruct userMapStruct;
    private final PasswordEncoder encoder;
    private final MailUtil mailUtil;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder jwtEncoder;

    @Value("${spring.mail.username}")
    private String appMail;

    @Override
    public AuthDto login(LogInDto logInDto) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(logInDto.email(), logInDto.password());

        authentication = daoAuthenticationProvider.authenticate(authentication);

        //create time
        Instant now = Instant.now();

        //
        String scope = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(authentication.getName())
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .claim("scope", scope)
                .build();

        String accessToken = jwtEncoder.encode(
                JwtEncoderParameters.from(jwtClaimsSet)
        ).getTokenValue();

        return new AuthDto("Bearer", accessToken);
    }

    @Transactional
    @Override
    public void register(RegisterDto registerDto) {
        User user = userMapStruct.fromRegisterDto(registerDto);
        user.setIsVerified(false);
        user.setPassword(encoder.encode(user.getPassword()));
        log.info("User {} ", user);

        if (authRepository.register(user)){
            //create user role
            for (Integer role : registerDto.roleIds()){
                authRepository.createUserRoles(user.getId(), role);
            }
        }

    }

    @Override
    public void verify(String email) {

        //find user email first
        User user = authRepository.selectByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Email Is Not Found"));

        String verifiedCode = UUID.randomUUID().toString();

        if (authRepository.updateVerifiedCode(email, verifiedCode)){
            user.setVerifiedCode(verifiedCode);
        }else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "User cannot be verified");
        }

       MailUtil.Meta<?> meta = MailUtil.Meta.builder()
               .to(email)
               .from(appMail)
               .subject("Account Verification")
               .templateUrl("auth/verify")
               .data(user)
               .build();

        try {
            mailUtil.send(meta);
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }

    }

    @Override
    public void checkVerify(String email, String verifiedCode) {

        User user = authRepository.selectByEmailAndVerifiedCode(email, verifiedCode).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User Is Not Exist"));
        if (!user.getIsVerified()){
            authRepository.updateIsVerifiedStatus(email, verifiedCode);
        }

    }

}
