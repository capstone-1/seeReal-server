package com.seereal.algi.security.jwt;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.seereal.algi.security.SecurityConstants.TOKEN_PREFIX;

@Component
public class HeaderTokenExtractor {
    public String extract(String header) {
        if (StringUtils.isEmpty(header) | header.length() < TOKEN_PREFIX.length()) {
            throw new InvalidJwtException("Invalid Token Information!");
        }
        return header.substring(TOKEN_PREFIX.length(), header.length());
    }
}
