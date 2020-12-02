package com.seereal.algi.service;

import com.seereal.algi.config.constant.S3Constants;
import com.seereal.algi.dto.donation.SimpleDonationResponseDto;
import com.seereal.algi.model.donation.Donation;
import com.seereal.algi.model.donation.DonationRepository;
import com.seereal.algi.model.user.User;
import com.seereal.algi.model.user.UserRepository;
import com.seereal.algi.security.context.UserContext;
import com.seereal.algi.service.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.seereal.algi.config.constant.S3Constants.DONATION_IMAGE;

@Service
public class PortfolioService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private S3Util s3Util;

    public PagedModel<EntityModel<SimpleDonationResponseDto>> getFavoriteDonations(Pageable pageable, UserContext context) {
        User user = userRepository.findByEmail(context.getPassword()).orElseThrow(() -> new NoSuchElementException("User Not Found!"));
        List<SimpleDonationResponseDto> favoriteDonations = user.getFavoriteDonations().stream().map(donation -> new SimpleDonationResponseDto(donation,
                s3Util.generateURL(S3Constants.DONATION_PREFIX, String.valueOf(donation.getId()), DONATION_IMAGE))).collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), favoriteDonations.size());
        Page<SimpleDonationResponseDto> page = new PageImpl<>(favoriteDonations.subList(start, end), pageable, favoriteDonations.size());
        PagedResourcesAssembler<SimpleDonationResponseDto> assembler = new PagedResourcesAssembler<>(null, null);
        return assembler.toModel(page);
    }
}
