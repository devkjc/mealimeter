package com.toy.mealimeter.meet.repository;

import com.toy.mealimeter.meet.domain.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetRepository extends JpaRepository<Meet, Long> {
}
