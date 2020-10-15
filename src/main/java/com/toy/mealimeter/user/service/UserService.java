package com.toy.mealimeter.user.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.toy.mealimeter.config.security.SecurityService;
import com.toy.mealimeter.user.domain.User;
import com.toy.mealimeter.user.dto.UserDto;
import com.toy.mealimeter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(203), "회원이 아닙니다. 회원가입이 필요합니다."));

        return ResponseEntity.ok(UserDto.Res.of(user));
    }

    public ResponseEntity<UserDto.Res> join(UserDto.Req req) {

        User authUser = SecurityService.getUser();
        authUser.updateUser(req);

        User user = userRepository.save(authUser);

        return ResponseEntity.ok(UserDto.Res.of(user));
    }

    public ResponseEntity<String> createToken() {

        User authUser = SecurityService.getUser();
        String uid = authUser.getUid();

        Map<String, Object> custom = new HashMap<>();

        custom.put("test", "Test2");

        try {
            return ResponseEntity.ok(FirebaseAuth.getInstance().createCustomToken(uid, custom));
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
