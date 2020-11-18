package com.toy.mealimeter.user.repository;

import com.toy.mealimeter.user.domain.Area;
import com.toy.mealimeter.user.dto.AreaDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {

    List<Area> findByUid(String uid);

    int countByUid(String uid);


}
