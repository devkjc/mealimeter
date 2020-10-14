package com.toy.mealimeter.user.service;

import com.toy.mealimeter.config.security.SecurityService;
import com.toy.mealimeter.user.domain.User;
import com.toy.mealimeter.user.dto.UserDto;
import com.toy.mealimeter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public ResponseEntity<UserDto.Res> login() {

        User authUser = SecurityService.getUser();
        String uid = authUser.getUid();
        User user = userRepository.findById(uid).orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(203), "회원이 아닙니다. 회원가입이 필요합니다."));

        return ResponseEntity.ok(UserDto.Res.of(user));
    }

    public ResponseEntity<UserDto.Res> join(UserDto.Req req) {

        User authUser = SecurityService.getUser();
        authUser.updateUser(req);

        User user = userRepository.save(authUser);

        return ResponseEntity.ok(UserDto.Res.of(user));
    }
}
