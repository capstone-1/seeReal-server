package com.seereal.algi.config.auth;

import com.seereal.algi.model.user.Role;
import com.seereal.algi.security.filter.JwtAuthenticationFilter;
import com.seereal.algi.security.filter.OrganizationLoginFilter;
import com.seereal.algi.security.handler.JwtAuthenticationFailureHandler;
import com.seereal.algi.security.handler.LoginAuthenticationFailureHandler;
import com.seereal.algi.security.handler.LoginAuthenticationSuccessHandler;
import com.seereal.algi.security.handler.OAuthAuthenticationSuccessHandler;
import com.seereal.algi.security.jwt.HeaderTokenExtractor;
import com.seereal.algi.security.provider.JwtAuthenticationProvider;
import com.seereal.algi.security.provider.OrganizationLoginProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.seereal.algi.config.constant.SecurityConstants.SKIP_URLS;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;
    private final LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
    private final OrganizationLoginProvider organizationLoginProvider;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final HeaderTokenExtractor headerTokenExtractor;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(organizationLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**",
                            "/js/**", "/h2/**", "/organization/**", "/campaign/**", "/regular-donation/**", "/portfolio/**")
                        .permitAll()
                    .antMatchers("/upload/**")
                        .hasRole(Role.AGENCY.name())
                    .antMatchers("/test/**")
                        .hasRole(Role.GUEST.name())
                    .anyRequest()
                        .authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                    .successHandler(oAuthAuthenticationSuccessHandler)
                        .userInfoEndpoint()// 로그인 씬 성공 이후 설정
                            .userService(customOAuth2UserService); // UserService 인터페이스 구현체 등록
    }

    protected OrganizationLoginFilter organizationLoginFilter() throws Exception {
        OrganizationLoginFilter filter = new OrganizationLoginFilter("/organization/login", loginAuthenticationSuccessHandler, loginAuthenticationFailureHandler);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    protected JwtAuthenticationFilter jwtFilter() throws Exception {
        FilterSkipMatcher matcher = new FilterSkipMatcher(SKIP_URLS, "/organization/**");
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, jwtAuthenticationFailureHandler, headerTokenExtractor);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.organizationLoginProvider)
            .authenticationProvider(this.jwtAuthenticationProvider);
    }
}