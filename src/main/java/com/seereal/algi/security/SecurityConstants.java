package com.seereal.algi.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 900_000; //15 mins
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    //TODO: Change To application.properties
    public static String SECRET_KEY = "jwttest";
    public static String ISSUER = "capstone";
}
