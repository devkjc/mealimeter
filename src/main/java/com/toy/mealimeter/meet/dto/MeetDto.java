package com.toy.mealimeter.meet.dto;

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
import java.time.LocalDate;
import java.util.List;

public class MeetDto {

    @Getter
    @ToString
    @ApiModel(value = "MeetDto.Req")
    public static class Req{

        @NotBlank(message = "필수값입니다.")
        private String title;

        @NotBlank(message = "필수값입니다.")
        private Gender gender;

        private int minAge;
        private int maxAge;

        private int maxNumber;

        private LocalDate startDate;
        private LocalDate endDate;

        private String restaurantName;
        private String restaurantAddress;

        public Meet toEntity(User meetMaster) {
            return Meet.builder()
                    .title(title)
                    .gender(gender)
                    .minAge(minAge)
                    .maxAge(maxAge)
                    .meetMaster(meetMaster)
                    .maxNumber(maxNumber)
                    .startDate(startDate)
                    .endDate(endDate)
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

        private final LocalDate startDate;
        private final LocalDate endDate;

        private final String restaurantName;
        private final String restaurantAddress;

        public static MeetDto.Res of(Meet meet) {
            return Res.builder()
                    .id(meet.getId())
                    .title(meet.getTitle())
                    .meetStatus(meet.getMeetStatus())
                    .gender(meet.getGender())
                    .minAge(meet.getMinAge())
                    .maxAge(meet.getMaxAge())
                    .maxNumber(meet.getMaxNumber())
                    .applyUserList(ApplyUserDto.Res.listOf(meet.getApplyUserList()))

                    .build();
        }

    }

}
