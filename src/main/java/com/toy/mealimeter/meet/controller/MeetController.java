package com.toy.mealimeter.meet.controller;

import com.toy.mealimeter.config.security.SecurityService;
import com.toy.mealimeter.meet.dto.MeetDto;
import com.toy.mealimeter.meet.service.MeetService;
import com.toy.mealimeter.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/meet")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
@Api(tags = {"Meet API"})
public class MeetController {

    private final MeetService meetService;

    @ApiOperation(value = "방 생성.")
    public ResponseEntity<MeetDto.Res> createMeet(MeetDto.Req req) {
        User user = SecurityService.getUser();
        return ResponseEntity.ok(meetService.createMeet(user, req));
    }

}
