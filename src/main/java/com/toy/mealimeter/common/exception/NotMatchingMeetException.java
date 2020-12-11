package com.toy.mealimeter.common.exception;

public class NotMatchingMeetException extends IllegalArgumentException {

    public NotMatchingMeetException() {
        super("접근 할 수 없는 방입니다.");
    }
}
