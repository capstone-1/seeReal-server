package com.seereal.algi.controller;

import com.seereal.algi.dto.regularDonation.RegularDonationSaveRequestDto;
import com.seereal.algi.service.RegularDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegularDonationController {
    @Autowired
    private RegularDonationService regularDonationService;
    // 정기기부 등록
    @PostMapping("/regular-donation")
    public void saveRegularDonation(@RequestBody RegularDonationSaveRequestDto requestDto) {
        regularDonationService.saveRegularDonation(requestDto);
    }

    //정기기부 조회

    //정기기부 결과 등록
    //정기기부 결과 조회 (모든 분기)
}
