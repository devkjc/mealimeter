package com.toy.mealimeter.user.controller;

import com.toy.mealimeter.config.security.SecurityService;
import com.toy.mealimeter.user.dto.AreaDto;
import com.toy.mealimeter.user.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/area")
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
@Api(tags = {"지역설정 API V1"})
public class AreaController {

    private final AreaService areaService;

    @ApiOperation(value = "지역 조회")
    @GetMapping
    public ResponseEntity<List<AreaDto.Res>> getArea() {
        String uid = SecurityService.getUserId();
        return ResponseEntity.ok(areaService.getArea(uid));
    }

    @ApiOperation(
            value = "지역 추가",
            notes = "최대 개수 2개 제한"
    )
    @PostMapping
    public ResponseEntity<AreaDto.Res> addArea(@Valid @RequestBody @ApiParam(name = "AreaDto.Req") AreaDto.Req req) {
        String uid = SecurityService.getUserId();
        return ResponseEntity.ok(areaService.addArea(uid, req));
    }

    @ApiOperation(
            value = "지역 삭제"
    )
    @DeleteMapping("/{areaId}")
    public ResponseEntity<?> deleteArea(@PathVariable long areaId) {
        String uid = SecurityService.getUserId();
        areaService.deleteArea(uid, areaId);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

    @PatchMapping("/{areaId}")
    public ResponseEntity<?> stateChangeTrue(@PathVariable long areaId) {
        String uid = SecurityService.getUserId();
        areaService.stateChangeTrue(uid, areaId);
        return ResponseEntity.ok("변경이 완료되었습니다.");
    }
}
