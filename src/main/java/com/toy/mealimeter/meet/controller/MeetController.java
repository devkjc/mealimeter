package com.toy.mealimeter.meet.controller;

import com.toy.mealimeter.config.security.SecurityService;
import com.toy.mealimeter.meet.domain.ApplyUser;
import com.toy.mealimeter.meet.dto.ApplyUserDto;
import com.toy.mealimeter.meet.dto.MeetDto;
import com.toy.mealimeter.meet.service.MeetService;
import com.toy.mealimeter.user.domain.User;
import com.toy.mealimeter.user.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/meet")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
@Api(tags = {"Meet API"})
public class MeetController {

    private final MeetService meetService;
    private final UserRepository userRepository;

    @ApiOperation(value = "방 조회.")
    @GetMapping
    public ResponseEntity<List<MeetDto.SimpleRes>> getMeetList(@RequestParam(defaultValue = "20") int pageSize,
                                                               @RequestParam(defaultValue = "1") int pageNum,
                                                               @RequestParam(value = "sort", defaultValue= "meetDate", required = false) String sort,
                                                               @RequestParam(value = "sortDir", defaultValue= "desc", required = false) String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.Direction.fromString(sortDir), sort);
        User user = userRepository.findByUid(SecurityService.getUserId());

        return ResponseEntity.ok(meetService.getMeetList(user, pageable));
    }

    @ApiOperation(value = "방 생성.")
    @PostMapping
    public ResponseEntity<MeetDto.Res> createMeet(@RequestBody @ApiParam(name = "MeetDto.Req") @Valid MeetDto.Req req) {
        User user = userRepository.findByUid(SecurityService.getUserId());
        return ResponseEntity.ok(meetService.createMeet(user, req));
    }

    @ApiOperation(value = "방 입장 신청")
    @PostMapping("/apply/{meetId}")
    public ResponseEntity<String> applyUser(@PathVariable long meetId) {

        User user = userRepository.findByUid(SecurityService.getUserId());
        meetService.addApplyList(user, meetId);

        return ResponseEntity.ok("신청이 완료되었습니다.");
    }

    @ApiOperation(value = "신청 취소")
    @DeleteMapping("/apply/{meetId}")
    public ResponseEntity<String> cancelApplyUser(@PathVariable long meetId) {
        User user = userRepository.findByUid(SecurityService.getUserId());
        meetService.cancelApplyUser(user, meetId);
        return ResponseEntity.ok("신청이 취소되었습니다.");
    }

    @ApiOperation(value = "신청 거절")
    @PatchMapping("/apply/{meetId}/{uId}")
    public ResponseEntity<String> rejectApplyUser(@PathVariable long meetId, @PathVariable String uId) {
        User master = userRepository.findByUid(SecurityService.getUserId());
        meetService.rejectApplyUser(master, meetId, uId);
        return ResponseEntity.ok("신청이 거절되었습니다.");
    }

    @ApiOperation(value = "신청자 조회")
    @GetMapping("/apply/{meetId}")
    public ResponseEntity<List<ApplyUserDto.Res>> getApplyList(@PathVariable long meetId) {

        User user = userRepository.findByUid(SecurityService.getUserId());

        return ResponseEntity.ok(meetService.getApplyList(user, meetId));
    }

    @ApiOperation(value = "내 신청목록 조회 (전체)")
    @GetMapping("/apply")
    public ResponseEntity<List<ApplyUserDto.Res>> getApplyUser() {
        User user = userRepository.findByUid(SecurityService.getUserId());
        return ResponseEntity.ok(meetService.getApplyUser(user));
    }

    @ApiOperation(value = "내 신청목록 조회")
    @GetMapping({"/apply/{applyStatus}"})
    public ResponseEntity<List<ApplyUserDto.Res>> getApplyUser(@PathVariable ApplyUser.ApplyStatus applyStatus) {
        User user = userRepository.findByUid(SecurityService.getUserId());
        return ResponseEntity.ok(meetService.getApplyUser(user, applyStatus));
    }

    @ApiOperation(value = "방 입장 승인")
    @PostMapping("/enter/{meetId}/{userId}")
    public ResponseEntity<String> enterUser(@PathVariable long meetId, @PathVariable String userId) {

        User user = userRepository.findByUid(SecurityService.getUserId());
        meetService.addEnterList(user, meetId, userId);

        return ResponseEntity.ok("입장 승인이 완료되었습니다.");
    }

    @ApiOperation(value = "방 나가기")
    @DeleteMapping("/enter/{meetId}")
    public ResponseEntity<String> exitEnterUser(@PathVariable long meetId) {
        User user = userRepository.findByUid(SecurityService.getUserId());
        meetService.exitEnterUser(user, meetId);
        return ResponseEntity.ok("방을 나갔습니다.");
    }

    @ApiOperation(value = "내 방 조회", notes = "현재 참여 중인 방의 정보를 조회.")
    @GetMapping("/my")
    public ResponseEntity<MeetDto.Res> getMeet() {
        User user = userRepository.findByUid(SecurityService.getUserId());
        return ResponseEntity.ok(meetService.getMeet(user));
    }

}
