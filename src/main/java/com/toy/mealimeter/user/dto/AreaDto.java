package com.toy.mealimeter.user.dto;

import com.toy.mealimeter.user.domain.Area;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class AreaDto {

    @Getter
    @ToString
    @ApiModel(value = "AreaDto.Req")
    public static class Req{

        @NotBlank(message = "지역을 입력해주세요")
        private String city;

        @NotNull(message = "지역을 입력해주세요")
        private String guGun;

        public Area toEntity(String uid) {
            return Area.builder()
                    .city(city)
                    .guGun(guGun)
                    .uid(uid)
                    .build();
        }

    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "AreaDto.Res")
    public static class Res {

        private final long id;
        private final String city;
        private final String guGun;
        private final Boolean mainStatus;

        public static List<AreaDto.Res> listOf(List<Area> areas) {
            return areas.stream().map(AreaDto.Res::of).collect(Collectors.toList());
        }

        public static AreaDto.Res of(Area area) {
            return Res.builder()
                    .id(area.getId())
                    .city(area.getCity())
                    .guGun(area.getGuGun())
                    .mainStatus(area.getMainStatus())
                    .build();
        }

    }
}
