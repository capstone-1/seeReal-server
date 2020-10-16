package com.seereal.algi.dto.campaigncost;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class CampaignCostRequestDto {
    private String useDate;
    private String content;
    private Integer cost;
}
