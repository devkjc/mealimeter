package com.toy.mealimeter.meet.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetArea {

    private String city;
    private String gugun;

}
