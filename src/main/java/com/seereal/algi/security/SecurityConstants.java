package com.seereal.algi.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 900_000; //15 mins
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final List<String> SKIP_URLS = Arrays.asList("/organization/login", "/organization/signup","/organization/find/register-number-by-email");
    //TODO: Change To application.properties
    public static String SECRET_KEY = "jwttest";
    public static String ISSUER = "capstone";
}
