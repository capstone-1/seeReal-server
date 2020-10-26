package com.seereal.algi.dto.activity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class ActivityRequestDto {
    private String name;
    private String content;
    private String performance;
    private String limitation;
}
