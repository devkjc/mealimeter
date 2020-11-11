package com.toy.mealimeter.user.controller;

import com.toy.mealimeter.user.dto.AreaDto;
import com.toy.mealimeter.user.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/")
    public ResponseEntity<List<AreaDto.Res>> getArea() {

        return areaService.getArea();
    }

    @ApiOperation(
            value = "지역 추가",
            notes = "최대 개수 2개 제한"
    )
    @PostMapping("/")
    public ResponseEntity<?> addArea(AreaDto.Req req) {
        return areaService.addArea(req);
    }

    @ApiOperation(
            value = "지역 삭제"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArea(@PathVariable long id) {
        return areaService.deleteArea(id);
    }
}
