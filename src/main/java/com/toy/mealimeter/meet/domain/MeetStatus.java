package com.toy.mealimeter.meet.domain;

public enum MeetStatus {

    Recruiting,
    End,
    Cancel,
    Shortage,
    Delete;

    public static MeetStatus statusCheck(Meet meet) {

        return Recruiting;
    }

}
