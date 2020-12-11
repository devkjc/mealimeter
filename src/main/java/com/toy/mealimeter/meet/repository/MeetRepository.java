package com.toy.mealimeter.meet.repository;

import com.toy.mealimeter.meet.domain.Meet;
import com.toy.mealimeter.meet.domain.MeetArea;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetRepository extends JpaRepository<Meet, Long> {

    List<Meet> findByMeetArea(MeetArea meetArea, Pageable pageable);

}
