package com.toy.mealimeter.meet.dto;

import com.toy.mealimeter.meet.domain.EnterUser;
import com.toy.mealimeter.meet.domain.Meet;
import com.toy.mealimeter.meet.domain.MeetStatus;
import com.toy.mealimeter.user.domain.Gender;
import com.toy.mealimeter.user.domain.User;
import com.toy.mealimeter.user.dto.UserDto;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MeetDto {

    @Getter
    @ToString
    @ApiModel(value = "MeetDto.Req")
    public static class Req {

        @NotBlank(message = "필수값입니다.")
        private String title;

        @NotNull(message = "필수값입니다.")
        private Gender gender;

        private int minAge;
        private int maxAge;

        private int maxNumber;

        private LocalDateTime meetDate;

        private String restaurantName;
        private String restaurantAddress;

        public Meet toEntity(User meetMaster) {
            return Meet.builder()
                    .title(title)
                    .gender(gender)
                    .minAge(minAge)
                    .maxAge(maxAge)
                    .meetMaster(meetMaster)
                    .meetArea(meetMaster.getActiveArea())
                    .maxNumber(maxNumber)
                    .meetDate(meetDate)
                    .restaurantAddress(restaurantAddress)
                    .restaurantName(restaurantName)
                    .build();
        }

    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "MeetDto.Res")
    public static class Res {

        private final long id;

        private final String title;

        private final MeetStatus meetStatus;

        private final Gender gender;

        private final int minAge;
        private final int maxAge;

        private final int maxNumber;

        private final List<ApplyUserDto.Res> applyUserList;
        private final List<EnterUserDto.Res> enterUserList;

        private final UserDto.Res meetMaster;

        private final LocalDateTime meetDate;

        private final String restaurantName;
        private final String restaurantAddress;

        public static List<MeetDto.Res> listOf(List<Meet> list) {
            if (list == null || list.isEmpty()) {
                return null;
            } else {
                return list.stream().map(MeetDto.Res::of).collect(Collectors.toList());
            }
        }

        public static MeetDto.Res of(Meet meet) {

            if (meet == null) {
                return null;
            } else {
                return Res.builder()
                        .id(meet.getId())
                        .title(meet.getTitle())
                        .meetStatus(meet.getMeetStatus())
                        .gender(meet.getGender())
                        .minAge(meet.getMinAge())
                        .maxAge(meet.getMaxAge())
                        .maxNumber(meet.getMaxNumber())
                        .meetDate(meet.getMeetDate())
                        .meetMaster(UserDto.Res.of(meet.getMeetMaster()))
                        .applyUserList(ApplyUserDto.Res.listOf(meet.getApplyUserList()))
                        .enterUserList(EnterUserDto.Res.listOf(meet.getEnterUserList()))
                        .build();
            }
        }

    }

    @Getter
    @Builder
    @ToString
    @ApiModel(value = "MeetDto.SimpleRes")
    public static class SimpleRes {

        private final long id;

        private final String title;

        private final MeetStatus meetStatus;

        private final Gender gender;

        private final int minAge;
        private final int maxAge;

        private final int maxNumber;
        private final int currentEnterNumber;

        private final LocalDateTime meetDate;

        private final UserDto.SimpleRes meetMaster;

        private final String restaurantName;
        private final String restaurantAddress;

        public static List<MeetDto.SimpleRes> listOf(List<Meet> list) {
            if (list == null || list.isEmpty()) {
                return null;
            } else {
                return list.stream().map(MeetDto.SimpleRes::of).collect(Collectors.toList());
            }
        }

        public static MeetDto.SimpleRes of(Meet meet) {
            return SimpleRes.builder()
                    .id(meet.getId())
                    .title(meet.getTitle())
                    .meetStatus(meet.getMeetStatus())
                    .gender(meet.getGender())
                    .minAge(meet.getMinAge())
                    .maxAge(meet.getMaxAge())
                    .maxNumber(meet.getMaxNumber())
                    .meetDate(meet.getMeetDate())
                    .meetMaster(UserDto.SimpleRes.of(meet.getMeetMaster()))
                    .currentEnterNumber(meet.getEnterUserList().size())
                    .restaurantName(meet.getRestaurantName())
                    .build();
        }

    }

}
