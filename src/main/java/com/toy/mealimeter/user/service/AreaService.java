package com.toy.mealimeter.user.service;

import com.toy.mealimeter.config.security.SecurityService;
import com.toy.mealimeter.user.domain.Area;
import com.toy.mealimeter.user.dto.AreaDto;
import com.toy.mealimeter.user.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;

    public List<AreaDto.Res> getArea(String uid) {
        
        return areaRepository.findByUid(uid).stream().map(AreaDto.Res::of).collect(toList());
    }

    @Transactional
    public AreaDto.Res addArea(String uid, AreaDto.Req req) {

        
        List<Area> byUid = areaRepository.findByUid(uid);

        if (byUid.size() >= 2) {
            throw new IllegalArgumentException("최대 지역 개수를 초과하였습니다.");
        }

        stateAllChange(byUid, false);

        return AreaDto.Res.of(areaRepository.save(req.toEntity(uid)));
    }

    @Transactional
    public void stateAllChange(List<Area> byUid, Boolean state) {
        List<Area> areaList = byUid.stream().map(area -> area.stateChange(state)).collect(toList());
        areaRepository.saveAll(areaList);
    }

    @Transactional
    public void stateChangeTrue(String uid, long areaId) {

        List<Area> byUid = areaRepository.findByUid(uid);
        List<Area> areaList = byUid.stream().peek(area -> area.stateChange(area.getId() == areaId)).collect(toList());

        areaRepository.saveAll(areaList);
    }

    @Transactional
    public void deleteArea(String uid, long areaId) {

        areaRepository.deleteById(areaId);

        List<Area> byUid = areaRepository.findByUid(uid);
        stateAllChange(byUid, true);
    }
}
