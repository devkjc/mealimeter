package com.toy.mealimeter.meet.repository;

import com.toy.mealimeter.meet.domain.ApplyUser;
import com.toy.mealimeter.meet.domain.EnterStatus;
import com.toy.mealimeter.meet.domain.EnterUser;
import com.toy.mealimeter.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyUserRepository extends JpaRepository<ApplyUser, Long> {

    List<ApplyUser> findByUserAndApplyStatus(User user, ApplyUser.ApplyStatus applyStatus);
    List<ApplyUser> findByUser(User user);

}
