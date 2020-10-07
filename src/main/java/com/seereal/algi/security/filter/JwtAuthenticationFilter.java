package com.seereal.algi.security.filter;

import com.seereal.algi.security.handler.JwtAuthenticationFailureHandler;
import com.seereal.algi.security.jwt.HeaderTokenExtractor;
import com.seereal.algi.security.token.JwtPreAuthorizationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.seereal.algi.security.SecurityConstants.HEADER_STRING;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private JwtAuthenticationFailureHandler failureHandler;
    private HeaderTokenExtractor extractor;

    protected JwtAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    public JwtAuthenticationFilter(RequestMatcher requestMatcher, JwtAuthenticationFailureHandler failureHandler, HeaderTokenExtractor extractor) {
        super(requestMatcher);
        this.failureHandler = failureHandler;
        this.extractor = extractor;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String tokenPayload = request.getHeader(HEADER_STRING);
        JwtPreAuthorizationToken token = new JwtPreAuthorizationToken(extractor.extract(tokenPayload));
        return super.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
