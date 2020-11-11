package com.toy.mealimeter.user.service;

import com.toy.mealimeter.config.security.SecurityService;
import com.toy.mealimeter.user.domain.User;
import com.toy.mealimeter.user.dto.AreaDto;
import com.toy.mealimeter.user.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.ws.ServiceMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;

    public ResponseEntity<List<AreaDto.Res>> getArea() {
        String uid = SecurityService.getUserId();
        return ResponseEntity.ok(areaRepository.findByUid(uid));
    }

    @Transactional
    public ResponseEntity<?> addArea(AreaDto.Req req) {

        String uid = SecurityService.getUserId();
        if (isMaxArea(uid)) {
            return new ResponseEntity<>("최대 지역 개수를 초과하였습니다.", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(AreaDto.Res.of(areaRepository.save(req.toEntity())));
    }

    @Transactional
    public ResponseEntity<?> deleteArea(long id) {
        areaRepository.deleteById(id);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }


    private Boolean isMaxArea(String uid) {
        return areaRepository.countByUid(uid) > 1;
    }
}
