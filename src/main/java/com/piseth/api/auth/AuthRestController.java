package com.piseth.api.auth;

import com.piseth.api.auth.web.AuthDto;
import com.piseth.api.auth.web.LogInDto;
import com.piseth.api.auth.web.RegisterDto;
import com.piseth.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/login")
    public BaseRest<?> login(@Valid @RequestBody LogInDto logInDto) {

        // call service
        AuthDto authDto = authService.login(logInDto);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You have been logged in successfully")
                .timestamp(LocalDateTime.now())
                .data(authDto)
                .build();
    }

    @PostMapping("/register")
    public BaseRest<?> register(@RequestBody @Valid RegisterDto body) {

        authService.register(body);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("You Have Been Register Successfully")
                .timestamp(LocalDateTime.now())
                .data(body.email())
                .build();
    }

    @PostMapping("/verify")
    public BaseRest<?> verify(@RequestParam String email){
        authService.verify(email);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Please Check Your Email And Verify")
                .timestamp(LocalDateTime.now())
                .data(email)
                .build();
    }

    @GetMapping("/check-verify")
    public BaseRest<?> checkVerify(@RequestParam String email,
                                   @RequestParam String verifiedCode){

        log.info("email {}", email);
        log.info("verified code {} ", verifiedCode);

        authService.checkVerify(email, verifiedCode);

        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Email Verify Successfully")
                .timestamp(LocalDateTime.now())
                .data(email)
                .build();
    }


}
