package com.seereal.algi.service;

import com.seereal.algi.config.constant.S3Constants;
import com.seereal.algi.dto.donation.SimpleDonationResponseDto;
import com.seereal.algi.dto.portfolio.*;
import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.category.CategoryRepository;
import com.seereal.algi.model.donation.Donation;
import com.seereal.algi.model.donation.DonationRepository;
import com.seereal.algi.model.portfolio.Portfolio;
import com.seereal.algi.model.portfolio.PortfolioRepository;
import com.seereal.algi.model.user.User;
import com.seereal.algi.model.user.UserRepository;
import com.seereal.algi.security.context.UserContext;
import com.seereal.algi.service.util.S3Util;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.seereal.algi.config.constant.S3Constants.DONATION_IMAGE;

@Service
public class PortfolioService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private S3Util s3Util;

    public PagedModel<EntityModel<SimpleDonationResponseDto>> getFavoriteDonations(Pageable pageable, UserContext context) {
        User user = userRepository.findByEmail(context.getPassword()).orElseThrow(() -> new NoSuchElementException("User Not Found!"));
        List<SimpleDonationResponseDto> favoriteDonations = user.getInterestDonations().stream().map(donation -> new SimpleDonationResponseDto(donation,
                s3Util.generateURL(S3Constants.DONATION_PREFIX, String.valueOf(donation.getId()), DONATION_IMAGE))).collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), favoriteDonations.size());
        Page<SimpleDonationResponseDto> page = new PageImpl<>(favoriteDonations.subList(start, end), pageable, favoriteDonations.size());
        PagedResourcesAssembler<SimpleDonationResponseDto> assembler = new PagedResourcesAssembler<>(null, null);
        return assembler.toModel(page);
    }

    public EntityModel<PortfolioDto> addPortfolio(UserContext context, SimpleDonationResponseDto dto) {
        User user = userRepository.findByEmail(context.getPassword()).orElseThrow(() -> new NoSuchElementException("User Not Found!"));
        Portfolio portfolio = Portfolio.builder().name(dto.getName())
                .user(user)
                .categories(categoriestoString(dto.getCategories())).build();
        portfolioRepository.save(portfolio);
        return EntityModel.of(new PortfolioDto(portfolio));
    }

    public EntityModel<PortfolioListDto> getPortfolios(UserContext context) {
        User user = userRepository.findByEmail(context.getPassword()).orElseThrow(() -> new NoSuchElementException("User Not Found!"));
        return EntityModel.of(new PortfolioListDto(user.getPortfolios().stream().map(PortfolioDto::new).collect(Collectors.toList())));
    }

    public EntityModel<PortfolioAccountListDto> getPortfoliowithAccount(UserContext context) {
        User user = userRepository.findByEmail(context.getPassword()).orElseThrow(() -> new NoSuchElementException("User Not Found!"));
        return EntityModel.of(new PortfolioAccountListDto(user.getPortfolios().stream().map(PortfolioAccountDto::new).collect(Collectors.toList())));
    }

    public EntityModel<PortfolioCategoryListDto> getPortfolioCategories(UserContext context) {
        User user = userRepository.findByEmail(context.getPassword()).orElseThrow(() -> new NoSuchElementException("User Not Found!"));
        List<List<String>> categoryList = user.getPortfolios().stream().map(p -> Arrays.asList(p.getCategories().split(" "))).collect(Collectors.toList());
        Map<String, Integer> categoryMap = new HashMap<>();
        for (List<String> categories: categoryList) {
            for (String category: categories) {
                if (!categoryMap.containsKey(category)) {
                    categoryMap.put(category, 1);
                } else {
                    categoryMap.put(category, categoryMap.get(category) + 1);
                }
            }
        }
        List<PortfolioCategoryDto> portfolioCategoryDtos = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : categoryMap.entrySet()) {
            portfolioCategoryDtos.add(new PortfolioCategoryDto(entry.getKey(), entry.getValue()));
        }
        return EntityModel.of(new PortfolioCategoryListDto(portfolioCategoryDtos));
    }

    public String categoriestoString(List<String> categories) {
        StringBuilder name = new StringBuilder();
        for(String category:categories) {
            name.append(category);
            name.append(" ");
        }
        return name.substring(0, name.length()-1);
    }
}
