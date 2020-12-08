package com.toy.mealimeter.meet.dto;

import com.toy.mealimeter.meet.domain.ApplyUser;
import com.toy.mealimeter.meet.domain.EnterUser;
import com.toy.mealimeter.user.dto.UserDto;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

public class EnterUserDto {

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "EnterUserDto.Res")
    public static class Res {

        private long id;
        private UserDto.Res user;

        public static List<EnterUserDto.Res> listOf(List<EnterUser> areas) {
            return areas.stream().map(EnterUserDto.Res::of).collect(Collectors.toList());
        }

        public static EnterUserDto.Res of(EnterUser enterUser) {
            return Res.builder()
                    .id(enterUser.getId())
                    .user(UserDto.Res.of(enterUser.getUser()))
                    .build();
        }

    }

}
