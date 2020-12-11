package com.toy.mealimeter.user.domain;

import com.toy.mealimeter.common.domain.BaseTimeEntity;
import com.toy.mealimeter.meet.domain.Meet;
import com.toy.mealimeter.meet.domain.MeetArea;
import com.toy.mealimeter.user.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends BaseTimeEntity {

    @Id
    private String uid;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(unique = true, nullable = false, length = 10)
    private String nickName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birth;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private final UserStatus status = UserStatus.A;

    @OneToMany(mappedBy = "uid", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Area> areas = new ArrayList<>();

    @Builder.Default
    private final int popularity = 0;

    @Builder.Default
    private Boolean pushAgree = false;

    @Column
    private String deviceKey;

    @Getter
    public enum UserStatus{
        A("활성화"),
        D("비활성화");

        private final String value;

        UserStatus(String value) {
            this.value = value;
        }
    }

    public User joinUser(UserDto.Req req) {

        this.nickName = req.getNickName();
        this.gender = req.getGender();
        this.birth = req.getBirth();

        return this;
    }

    public User updateUser(UserDto.Req req) {

        this.nickName = req.getNickName();
        this.gender = req.getGender();
        this.birth = req.getBirth();
        this.pushAgree = req.getPushAgree();
        this.deviceKey = req.getDeviceKey();

        return this;
    }

    public Boolean genderCheck(Gender gender) {
        return gender.equals(Gender.N) || gender.equals(this.gender);
    }

    public int getAge() {
        LocalDate now = LocalDate.now();
        int age = now.minusYears(birth.getYear()).getYear(); // (1)
        if (birth.plusYears(age).isAfter(now)) age = age -1;
        return age;
    }

    public MeetArea getActiveArea() {
        return this.areas.stream().filter(Area::getMainStatus).findFirst().orElseThrow(()-> new IllegalArgumentException("지역을 설정해주세요.")).meetArea();
    }

    public Boolean ageCheck(int minAge, int maxAge) {
        return minAge <= getAge() && getAge() <= maxAge;
    }

    public Boolean meetCheck(Meet meet) {
        return genderCheck(meet.getGender()) && ageCheck(meet.getMinAge(), meet.getMaxAge());
    }

}

