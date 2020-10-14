package com.toy.mealimeter.user.controller;

import com.toy.mealimeter.config.security.SecurityService;
import com.toy.mealimeter.user.dto.UserDto;
import com.toy.mealimeter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public String user() {
        return SecurityService.getUser().toString();
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto.Res> login() {
        return userService.login();
    }

    @PostMapping("/join")
    public ResponseEntity<UserDto.Res> join(@Valid @RequestBody UserDto.Req req) {
        log.info(req);
        return userService.join(req);
    }

}
