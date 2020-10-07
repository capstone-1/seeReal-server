package com.seereal.algi.security.jwt;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HeaderTokenExtractor {
    public static final String HEADER_PREFIX = "Bearer ";
        public String extract(String header) {
        if (StringUtils.isEmpty(header) | header.length() < HEADER_PREFIX.length()) {
            throw new InvalidJwtException("Invalid Token Information!");
        }
        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
