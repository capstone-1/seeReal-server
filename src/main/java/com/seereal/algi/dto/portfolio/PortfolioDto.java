package com.seereal.algi.dto.portfolio;import com.seereal.algi.model.portfolio.Portfolio;import lombok.Getter;import lombok.NoArgsConstructor;import lombok.Setter;import java.util.ArrayList;import java.util.Arrays;import java.util.List;@NoArgsConstructor@Getter@Setterpublic class PortfolioDto {    private String name;    private List<String> categories = new ArrayList<>();    public PortfolioDto(Portfolio portfolio) {        this.name = portfolio.getName();        this.categories.addAll(Arrays.asList(portfolio.getCategories().split(" ")));    }}