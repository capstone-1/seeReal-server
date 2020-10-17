package com.seereal.algi.model.campaignReview;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class OrganizationCampignReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String workName;
    private Integer workFee;
    private Integer workNumber;
    private String workEtc;
    private String itemName;
    private Integer itenNumber;
    private String itemShop;
    private Integer itenFee;
    private String workReceiptUrl;
    private String itemReceiptUrl;

    @Builder
    public OrganizationCampignReview(String content, String workName, Integer workFee, Integer workNumber, String workEtc, String itemName, Integer itenNumber, String itemShop, Integer itenFee) {
        this.content = content;
        this.workName = workName;
        this.workFee = workFee;
        this.workNumber = workNumber;
        this.workEtc = workEtc;
        this.itemName = itemName;
        this.itenNumber = itenNumber;
        this.itemShop = itemShop;
        this.itenFee = itenFee;
    }

    public void setWorkReceiptUrl(String workReceiptUrl) {
        this.workReceiptUrl = workReceiptUrl;
    }
    public void setItemReceiptUrl(String itemReceiptUrl) {
        this.itemReceiptUrl = itemReceiptUrl;
    }
}
