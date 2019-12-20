package com.example.udacitycource.security;

public class SecurityConstants {
    public static final String SECRECT = "SecretKeyToGenJWTs";
    public static final Long EXPIRATION_TIME = 864000000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
}
