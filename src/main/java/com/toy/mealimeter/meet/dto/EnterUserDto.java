package com.toy.mealimeter.meet.dto;

import com.toy.mealimeter.meet.domain.EnterUser;
import com.toy.mealimeter.user.dto.UserDto;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class EnterUserDto {

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "EnterUserDto.Res")
    public static class Res {

        private long id;
        private UserDto.Res user;

        public static EnterUserDto.Res of(EnterUser enterUser) {
            return Res.builder()
                    .id(enterUser.getId())
                    .user(UserDto.Res.of(enterUser.getUser()))
                    .build();
        }

    }

}
