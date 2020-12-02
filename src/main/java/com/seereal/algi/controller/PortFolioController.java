package com.seereal.algi.controller;

import com.seereal.algi.dto.donation.SimpleDonationResponseDto;
import com.seereal.algi.security.context.UserContext;
import com.seereal.algi.security.jwt.HeaderTokenExtractor;
import com.seereal.algi.security.jwt.JwtDecoder;
import com.seereal.algi.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortFolioController {
    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private HeaderTokenExtractor extractor;

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/portfolio/favorite-donation")
    public ResponseEntity<PagedModel<EntityModel<SimpleDonationResponseDto>>> getFavoriteDonations(@RequestHeader(value = "Authorization") String tokenPayload,
                                                                                                   @PageableDefault(size = 10) Pageable pageable) {
        String token = extractor.extract(tokenPayload);
        UserContext userContext = jwtDecoder.decodeJwtForUser(token);
        return new ResponseEntity<>(portfolioService.getFavoriteDonations(pageable, userContext), HttpStatus.OK);
    }
}