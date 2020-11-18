package com.toy.mealimeter.user.service;

import com.toy.mealimeter.config.security.SecurityService;
import com.toy.mealimeter.user.domain.User;
import com.toy.mealimeter.user.dto.UserDto;
import com.toy.mealimeter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto.Res login() {

        User user = getFindAuthUser();

        return UserDto.Res.of(user);
    }


    public UserDto.Res join(UserDto.Req req) {

        User authUser = getAuthUser();
        authUser.updateUser(req);

        User user = userRepository.save(authUser);

        return UserDto.Res.of(user);
    }

    public static User getAuthUser() {
        return SecurityService.getUser();
    }

    public static String getAuthUid() {
        User authUser = getAuthUser();
        return authUser.getUid();
    }

    public User getFindAuthUser() {
        return userRepository.findById(getAuthUid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.valueOf(203), "회원이 아닙니다. 회원가입이 필요합니다."));
    }

    public Boolean nickNameDuplication(String nickName) {
        return nickNameCheck(nickName);
    }

    private boolean nickNameCheck(String nickName) {
        return userRepository.countByNickName(nickName) < 1;
    }
}
