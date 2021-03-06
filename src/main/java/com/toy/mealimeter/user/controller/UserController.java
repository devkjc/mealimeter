package com.toy.mealimeter.user.controller;

import com.toy.mealimeter.config.security.SecurityService;
import com.toy.mealimeter.user.dto.UserDto;
import com.toy.mealimeter.user.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
@Api(tags = {"유저 API V1"})
public class UserController {

    private final UserService userService;

    @ApiOperation(
            value = "JWT Token 로그인",
            notes = "회원이 없을 시 203 Response 추가정보 입력 후 /join 으로 유도 필요.")
    @ApiResponses(value = {@ApiResponse(code = 203, message = "추가정보 입력 후 /join 으로 유도 필요.", response = Exception.class)})
    @PostMapping("/login")
    public ResponseEntity<UserDto.Res> login() {
        return ResponseEntity.ok(userService.login());
    }

    @ApiOperation(
            value = "회원 가입.")
    @PostMapping("/join")
    public ResponseEntity<UserDto.Res> join(@Valid @RequestBody @ApiParam(name = "UserDto.Req") UserDto.Req req) {
        log.info(req);
        return ResponseEntity.ok(userService.join(req));
    }

    @ApiOperation(
            value = "WEB 회원 가입.", notes = "회원 가입시 지역 자동 설정.")
    @PostMapping("/web/join")
    public ResponseEntity<UserDto.Res> webJoin(@Valid @RequestBody @ApiParam(name = "UserDto.Req") UserDto.Req req) {
        log.info(req);
        return ResponseEntity.ok(userService.webJoin(req));
    }

    @ApiOperation(
            value = "닉네임 중복 검사",
            notes = "사용 가능 시 true 중복 시 false"
    )
    @PostMapping("/nickName")
    public ResponseEntity<Boolean> nickNameDuplication(
            @RequestBody String nickName
    ) {

        return ResponseEntity.ok(userService.nickNameDuplication(nickName));
    }

    @GetMapping("/{uId}")
    public ResponseEntity<UserDto.SimpleRes> getSimpleUser(@PathVariable String uId) {

        return ResponseEntity.ok(userService.getSimpleUser(uId));
    }

    @DeleteMapping
    private ResponseEntity<?> deleteMember() {
        String uid = SecurityService.getUserId();
        userService.deleteMember(uid);
        return ResponseEntity.ok("Delete Complete");

    }
}
