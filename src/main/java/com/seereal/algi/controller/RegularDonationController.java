package com.seereal.algi.controller;

import com.seereal.algi.dto.regularDonation.DetailRegularDonationResponseDto;
import com.seereal.algi.dto.regularDonation.RegularDonationSaveRequestDto;
import com.seereal.algi.dto.regularDonation.SimpleRegularDonationResponseDto;
import com.seereal.algi.service.RegularDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegularDonationController {
    @Autowired
    private RegularDonationService regularDonationService;
    // 정기기부 등록
    @PostMapping("/regular-donation")
    public String saveRegularDonation(@RequestBody RegularDonationSaveRequestDto requestDto) {
        return regularDonationService.saveRegularDonation(requestDto);
    }

    //정기기부 목록 조회
    @GetMapping("/regular-donation")
    public List<SimpleRegularDonationResponseDto> getRegularDonationList() {
        return regularDonationService.getRegularDonationList();
    }
    //정기기부 상세 조회
    @GetMapping("/regular-donation/{name}")
    public DetailRegularDonationResponseDto getRegularDonationDetail(@PathVariable String name) {
        return regularDonationService.getRegularDonationDetail(name);
    }

    //정기기부 결과 등록
    //정기기부 결과 조회 (모든 분기)
}
