package com.toy.mealimeter.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toy.mealimeter.user.domain.Area;
import com.toy.mealimeter.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    @Getter
    @ToString
    @ApiModel(value = "UserDto.Req")
    public static class Req{
        @Size(min = 2, max = 10, message = "닉네임 글자 수는 2글자 이상 10글자 이하 입니다.")
        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nickName;

        @NotNull(message = "성별을 입력해주세요.")
        private User.Gender gender;

        @NotNull(message = "생년월일을 입력해주세요.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @ApiModelProperty(value = "format : yyyy-MM-dd")
        private LocalDate birth;

        private Boolean pushAgree;

        private String deviceKey;
    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "UserDto.Res")
    public static class Res {

        private final String uid;
        private final String name;
        private final String email;
        private final String nickName;
        private final User.Gender gender;
        @ApiParam(value = "yyyy-MM-dd")
        private final LocalDate birth;
        private final List<AreaDto.Res> areas;
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
                    .areas(AreaDto.Res.listOf(user.getAreas()))
                    .pushAgree(user.getPushAgree())
                    .deviceKey(user.getDeviceKey())
                    .build();
        }

    }
}
