package com.toy.mealimeter.common.exception;

public class NoUserException extends IllegalArgumentException {

    public NoUserException() {
        super("존재하지 않은 유저입니다.");
    }
}
