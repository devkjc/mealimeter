package com.toy.mealimeter.meet.controller;

import com.toy.mealimeter.config.security.SecurityService;
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

@RestController
@RequestMapping("/api/v1/meet")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
@Api(tags = {"Meet API"})
public class MeetController {

    private final MeetService meetService;
    private final UserRepository userRepository;

    @ApiOperation(value = "방 생성.")
    @PostMapping
    public ResponseEntity<MeetDto.Res> createMeet(@RequestBody @ApiParam(name = "MeetDto.Req") @Valid MeetDto.Req req) {
        User user = userRepository.findByUid(SecurityService.getUserId());
        return ResponseEntity.ok(meetService.createMeet(user, req));
    }

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

    @ApiOperation(value = "방 입장 신청")
    @PostMapping("/apply/{meetId}")
    public ResponseEntity<String> applyUser(@PathVariable long meetId) {

        User user = userRepository.findByUid(SecurityService.getUserId());
        meetService.addApplyList(user, meetId);

        return ResponseEntity.ok("신청이 완료되었습니다.");
    }

    @ApiOperation(value = "방 입장 승인")
    @PostMapping("/entger/{meetId}")
    public ResponseEntity<String> enterUser(@PathVariable long meetId) {

        User user = userRepository.findByUid(SecurityService.getUserId());
        meetService.addEnterList(user, meetId);

        return ResponseEntity.ok("신청이 완료되었습니다.");
    }
}
