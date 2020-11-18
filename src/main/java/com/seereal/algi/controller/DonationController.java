package com.seereal.algi.controller;
import com.seereal.algi.dto.donation.DonationCostResultDto;
import com.seereal.algi.dto.donation.DonationResultDto;
import com.seereal.algi.dto.donation.DetailDonationResponseDto;
import com.seereal.algi.dto.donation.DonationSaveRequestDto;
import com.seereal.algi.dto.donation.SimpleDonationResponseDto;
import com.seereal.algi.service.RegularDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class DonationController {
    @Autowired
    private RegularDonationService regularDonationService;
    // 정기기부 등록
    @PostMapping("/regular-donation")
    public String saveRegularDonation(@RequestBody DonationSaveRequestDto requestDto) {
        return regularDonationService.saveRegularDonation(requestDto);
    }

    //정기기부 목록 조회
    @GetMapping("/regular-donation")
    public ResponseEntity<PagedModel<EntityModel<SimpleDonationResponseDto>>> getRegularDonationList(@PageableDefault(size = 10) Pageable pageable,
                                                                                                     @RequestParam(value = "search") String name) {
        if (name.isEmpty()) {
            return new ResponseEntity<>(regularDonationService.getRegularDonationList(pageable), HttpStatus.OK);
        }
        return new ResponseEntity<>(regularDonationService.getRegularDonationList(pageable, name), HttpStatus.OK);
    }

    //정기기부 상세 조회
    @GetMapping("/regular-donation/{id}")
    public DetailDonationResponseDto getRegularDonationDetail(@PathVariable Long id) {
        return regularDonationService.getRegularDonationDetail(id);
    }

    //정기기부 분기별 결과 등록
    @PostMapping("/regular-donation/result/upload/{id}")
    public void saveRegularDonationResult(@RequestBody DonationResultDto.Request request,
                                          @PathVariable("id") Long id) {
        regularDonationService.saveDonationResult(request, id);
    }

    //정기기부 분기별 결과 내 지출내역 등록
    @PostMapping("/regular-donation/cost/upload/{id}")
    public void saveRegularDonationCostResult(@RequestBody DonationCostResultDto.Request request,
                                              @PathVariable("id") Long id,
                                              @RequestParam("quarter") Integer quarter) {
        regularDonationService.saveDonationCostResult(request, id, quarter);
    }

    //정기기부 분기별 결과 조회
    @GetMapping("/regular-donation/result/search/{id}")
    public ResponseEntity<DonationResultDto.Response> findRegularDonationResult(@PathVariable("id") Long id,
                                                    @PathParam("quarter") Integer quarter) {
        return ResponseEntity.ok(regularDonationService.findDonationResultByNameAndQuater(id, quarter));
    }

    // 승인된 정기 기부 검색 (검색 조건 : 카테고리)
    @GetMapping("regular-donation")
    public ResponseEntity<PagedModel<EntityModel<SimpleDonationResponseDto>>> getRegularDonationsByCategory(@PageableDefault(size = 10) Pageable pageable,
                                                                                                            @RequestParam(value = "category") String category) {
        return new ResponseEntity<>(regularDonationService.getRegularDonationsByCategory(pageable, category), HttpStatus.OK);
    }
}
