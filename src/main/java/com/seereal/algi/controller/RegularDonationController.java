package com.seereal.algi.controller;
import com.seereal.algi.dto.regularDonation.DonationCostResultDto;
import com.seereal.algi.dto.regularDonation.DonationResultDto;
import com.seereal.algi.dto.regularDonation.DetailRegularDonationResponseDto;
import com.seereal.algi.dto.regularDonation.RegularDonationSaveRequestDto;
import com.seereal.algi.dto.regularDonation.SimpleRegularDonationResponseDto;
import com.seereal.algi.service.RegularDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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

    //정기기부 분기별 결과 등록
    @PostMapping("/regular-donation/result/upload")
    public void saveRegularDonationResult(@RequestBody DonationResultDto.Request request,
                                          @RequestParam("name") String name) {
        regularDonationService.saveDonationResult(request, name);
    }

    //정기기부 분기별 결과 내 지출내역 등록
    @PostMapping("/regular-donation/cost/upload")
    public void saveRegularDonationCostResult(@RequestBody DonationCostResultDto.Request request,
                                              @RequestParam("name") String name,
                                              @RequestParam("quarter") Integer quarter) {
        regularDonationService.saveDonationCostResult(request, name, quarter);
    }

    //정기기부 분기별 결과 조회
    @GetMapping("/regular-donation/result/search")
    public ResponseEntity<DonationResultDto.Response> findRegularDonationResult(@PathParam("name") String name,
                                                    @PathParam("quarter") Integer quarter) {
        return ResponseEntity.ok(regularDonationService.findDonationResultByNameAndQuater(name, quarter));
    }

    // 승인된 정기 기부 검색 (검색 조건 : 카테고리)
    @GetMapping("regular-donation/category")
    public List<DetailRegularDonationResponseDto> getRegularDonationsByCategory(@RequestParam(value = "name") List<String> categories) {
        return regularDonationService.getRegularDonationsByCategory(categories);
    }
}
