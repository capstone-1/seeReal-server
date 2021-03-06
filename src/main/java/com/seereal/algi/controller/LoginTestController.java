package com.seereal.algi.controller;

import com.seereal.algi.config.auth.CustomOAuth2UserService;
import com.seereal.algi.config.auth.SessionUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class LoginTestController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}