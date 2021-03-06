package com.toy.mealimeter.common.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@RestController
@Api(tags = {"공통 API"})
public class CommonController {

    @ApiOperation(
            value = "서버 연결 테스트",
            notes = "서버 연결 테스트를 위해 인증 절차 필요 없음."
    )
    @GetMapping("/hello")
    public String user() {

        return "Hello Mealimeter !";
    }

}
