package com.toy.mealimeter.meet.repository;

import com.toy.mealimeter.meet.domain.EnterStatus;
import com.toy.mealimeter.meet.domain.EnterUser;
import com.toy.mealimeter.meet.domain.Meet;
import com.toy.mealimeter.meet.domain.MeetArea;
import com.toy.mealimeter.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterUserRepository extends JpaRepository<EnterUser, Long> {

    EnterUser findTopByUserAndEnterStatus(User user, EnterStatus enterStatus);

}
