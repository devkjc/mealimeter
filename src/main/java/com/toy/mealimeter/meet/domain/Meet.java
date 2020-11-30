package com.toy.mealimeter.meet.domain;

import com.toy.mealimeter.user.domain.Gender;
import com.toy.mealimeter.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private final MeetStatus meetStatus = MeetStatus.Recruiting;

    private Gender gender;

    private int minAge;
    private int maxAge;

    private int maxNumber;

    @OneToMany(mappedBy = "meet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ApplyUser> applyUserList;

    @OneToMany(mappedBy = "meet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EnterUser> enterUserList;

    @OneToOne
    @JoinColumn(name = "meet_master_uid_fk")
    private User meetMaster;

    private LocalDate startDate;
    private LocalDate endDate;

    private String restaurantName;
    private String restaurantAddress;

}
