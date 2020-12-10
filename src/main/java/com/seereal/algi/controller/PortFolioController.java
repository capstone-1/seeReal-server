package com.seereal.algi.controller;

import com.seereal.algi.dto.donation.SimpleDonationResponseDto;
import com.seereal.algi.dto.portfolio.PortfolioCategoryDto;
import com.seereal.algi.dto.portfolio.PortfolioDto;
import com.seereal.algi.security.context.UserContext;
import com.seereal.algi.security.jwt.HeaderTokenExtractor;
import com.seereal.algi.security.jwt.JwtDecoder;
import com.seereal.algi.service.PortfolioService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PortFolioController {
    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private HeaderTokenExtractor extractor;

    @Autowired
    private PortfolioService portfolioService;


    @GetMapping("/portfolio")
    public ResponseEntity<EntityModel<List<PortfolioDto>>> getPortfolios(@RequestHeader(value = "Authorization") String tokenPayload) {
        String token = extractor.extract(tokenPayload);
        UserContext userContext = jwtDecoder.decodeJwtForUser(token);
        return new ResponseEntity<>(portfolioService.getPortfolios(userContext), HttpStatus.OK);
    }

    @GetMapping("/portfolio/category-graph")
    public ResponseEntity<EntityModel<List<PortfolioCategoryDto>>> getPortfolioCategories(@RequestHeader(value = "Authorization") String tokenPayload) {
        String token = extractor.extract(tokenPayload);
        UserContext userContext = jwtDecoder.decodeJwtForUser(token);
        return new ResponseEntity<>(portfolioService.getPortfolioCategories(userContext), HttpStatus.OK);
    }

    @GetMapping("/portfolio/interest-donation")
    public ResponseEntity<PagedModel<EntityModel<SimpleDonationResponseDto>>> getInterestDonations(@RequestHeader(value = "Authorization") String tokenPayload,
                                                                                                   @PageableDefault(size = 10) Pageable pageable) {
        String token = extractor.extract(tokenPayload);
        UserContext userContext = jwtDecoder.decodeJwtForUser(token);
        return new ResponseEntity<>(portfolioService.getFavoriteDonations(pageable, userContext), HttpStatus.OK);
    }

    @PutMapping("/portfolio/add-portfolio")
    public ResponseEntity<EntityModel<PortfolioDto>> addPortfolio(@RequestHeader(value = "Authorization") String tokenPayload,
                                                                  @RequestBody SimpleDonationResponseDto dto) {
        String token = extractor.extract(tokenPayload);
        UserContext userContext = jwtDecoder.decodeJwtForUser(token);
        return new ResponseEntity<>(portfolioService.addPortfolio(userContext, dto), HttpStatus.OK);
    }
}