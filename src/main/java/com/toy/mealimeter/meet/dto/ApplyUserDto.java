package com.toy.mealimeter.meet.dto;

import com.toy.mealimeter.meet.domain.ApplyUser;
import com.toy.mealimeter.meet.domain.EnterUser;
import com.toy.mealimeter.meet.domain.Meet;
import com.toy.mealimeter.meet.domain.MeetStatus;
import com.toy.mealimeter.user.domain.Area;
import com.toy.mealimeter.user.domain.Gender;
import com.toy.mealimeter.user.domain.User;
import com.toy.mealimeter.user.dto.AreaDto;
import com.toy.mealimeter.user.dto.UserDto;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ApplyUserDto {

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "ApplyUserDto.Res")
    public static class Res {

        private final long id;
        private final UserDto.SimpleRes user;
        private final ApplyUser.ApplyStatus applyStatus;

        public static List<ApplyUserDto.Res> listOf(List<ApplyUser> areas) {
            if (areas == null || areas.isEmpty()) {
                return null;
            }else{
                return areas.stream().map(ApplyUserDto.Res::of).collect(Collectors.toList());
            }
        }

        public static ApplyUserDto.Res of(ApplyUser applyUser) {
            return Res.builder()
                    .id(applyUser.getId())
//                    .meet(MeetDto.Res.of(applyUser.getMeet()))
                    .user(UserDto.SimpleRes.of(applyUser.getUser()))
                    .applyStatus(applyUser.getApplyStatus())

                    .build();
        }

    }

}
