package com.toy.mealimeter.common.exception;

public class NotMeetMasterException extends IllegalArgumentException {

    public NotMeetMasterException() {
        super("방장이 아닙니다.");
    }
}
