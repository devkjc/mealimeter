package com.toy.mealimeter.user.repository;

import com.toy.mealimeter.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUid(String uid);

    int countByNickName(String nickName);
}
