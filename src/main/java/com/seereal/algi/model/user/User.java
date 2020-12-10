package com.seereal.algi.model.user;

import com.seereal.algi.model.BaseTimeEntity;
import com.seereal.algi.model.donation.Donation;
import com.seereal.algi.model.portfolio.Portfolio;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // Role: 직접 만드는 클래스
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<Donation> interestDonations = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Portfolio> portfolios = new ArrayList<>();

    @Builder
    public User(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User update(String name) {
        this.name = name;
        return this;
    }

    public void addFavoriteDonation(Donation donation) {
        this.interestDonations.add(donation);
    }
    public String getRoleKey() {
        return this.role.getKey();
    }
}