package com.urlshortener.auth;

import com.urlshortener.user.dto.AuthResponse;
import com.urlshortener.user.dto.LoginRequest;
import com.urlshortener.user.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}