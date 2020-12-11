package com.toy.mealimeter.meet.domain;

import com.toy.mealimeter.common.domain.BaseTimeEntity;
import com.toy.mealimeter.user.domain.Gender;
import com.toy.mealimeter.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meet extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private final MeetStatus meetStatus = MeetStatus.Recruiting;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int minAge;
    private int maxAge;

    private int maxNumber;

    @OneToMany(mappedBy = "meet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<ApplyUser> applyUserList = new ArrayList<>();

    @OneToMany(mappedBy = "meet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<EnterUser> enterUserList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "meet_master_uid_fk")
    private User meetMaster;

    @Embedded
    private MeetArea meetArea;

    private LocalDateTime meetDate;

    private String restaurantName;
    private String restaurantAddress;

    public void addEnterUser(User user) {

        EnterUser enterUser = EnterUser.builder()
                .user(user)
                .meet(this)
                .build();
        enterUserList.add(enterUser);
    }

    public void addApplyUser(User user) {

        ApplyUser applyUser = ApplyUser.builder()
                .user(user)
                .meet(this)
                .build();

        applyUserList.add(applyUser);
    }

}
