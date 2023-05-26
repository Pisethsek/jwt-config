package com.piseth.api.auth;

import com.piseth.api.auth.web.AuthDto;
import com.piseth.api.auth.web.LogInDto;
import com.piseth.api.auth.web.RegisterDto;

public interface AuthService {
    AuthDto login(LogInDto logInDto);
    void register(RegisterDto registerDto);
    void verify(String email);
    void checkVerify(String email, String verifiedCode);
}
