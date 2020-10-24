package com.seereal.algi.service;

import com.seereal.algi.dto.regularDonation.DonationCostPreviewRequestDto;
import com.seereal.algi.dto.regularDonation.RegularDonationSaveRequestDto;
import com.seereal.algi.model.regularDonation.DonationCostPreviewRepository;
import com.seereal.algi.model.regularDonation.RegularDonation;
import com.seereal.algi.model.regularDonation.RegularDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegularDonationService {
    @Autowired
    private RegularDonationRepository regularDonationRepository;
    @Autowired
    private DonationCostPreviewRepository donationCostPreviewRepository;

    public void saveRegularDonation(RegularDonationSaveRequestDto requestDto) {
        RegularDonation donation = RegularDonationSaveRequestDto.convertToEntity(requestDto);
        regularDonationRepository.save(donation);
        requestDto.getCostPreviews().stream().map(DonationCostPreviewRequestDto::convertToentity)
                .forEach(r -> {
                    r.setRegularDonation(donation);
                    donationCostPreviewRepository.save(r);
                });
        //TODO
        //  1. Add category
        //  2. Return Pre-signed Url for Profile
    }
}
