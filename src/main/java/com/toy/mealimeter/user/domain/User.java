package com.toy.mealimeter.user.domain;

import com.toy.mealimeter.common.domain.BaseTimeEntity;
import com.toy.mealimeter.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    private Boolean pushAgree;

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

    @Getter
    public enum Gender{
        M("남성"), // 남성
        F("여성"); // 여성

        private final String value;

        Gender(String value) {
            this.value = value;
        }

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
        return gender.equals(this.gender);
    }

    public int getAge() {
        LocalDate now = LocalDate.now();
        int age = now.minusYears(birth.getYear()).getYear(); // (1)
        if (birth.plusYears(age).isAfter(now)) age = age -1;
        return age;
    }

}

