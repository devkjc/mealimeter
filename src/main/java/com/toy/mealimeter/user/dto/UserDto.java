package com.toy.mealimeter.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toy.mealimeter.user.domain.Area;
import com.toy.mealimeter.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDto {

    @Getter
    @ToString
    public static class Req{
        @Size(min = 2, max = 10)
        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nickName;

        @NotNull(message = "성별을 입력해주세요.")
        private User.Gender gender;

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "생년월일을 입력해주세요.")
        private LocalDate birth;

        private Boolean pushAgree;

        private String deviceKey;
    }

    @Getter
    @Builder
    @ToString
    public static class Res {

        private final String uid;
        private final String name;
        private final String email;
        private final String nickName;
        private final User.Gender gender;
        private final LocalDate birth;
        private final List<Area> areas;
        private final Boolean pushAgree;
        private final String deviceKey;

        public static Res of(User user) {
            return Res.builder()
                    .uid(user.getUid())
                    .name(user.getName())
                    .email(user.getEmail())
                    .nickName(user.getNickName())
                    .gender(user.getGender())
                    .birth(user.getBirth())
                    .areas(user.getAreas())
                    .pushAgree(user.getPushAgree())
                    .deviceKey(user.getDeviceKey())
                    .build();
        }

    }
}