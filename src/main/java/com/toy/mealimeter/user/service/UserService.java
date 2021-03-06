package com.toy.mealimeter.user.service;

import com.toy.mealimeter.config.security.SecurityService;
import com.toy.mealimeter.user.domain.User;
import com.toy.mealimeter.user.dto.AreaDto;
import com.toy.mealimeter.user.dto.UserDto;
import com.toy.mealimeter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AreaService areaService;

    public UserDto.Res login() {

        User user = getFindAuthUser();

        return UserDto.Res.of(user);
    }

    @Transactional
    public UserDto.Res join(UserDto.Req req) {

        User authUser = getAuthUser();
        authUser.joinUser(req);

        if (nickNameDuplication(req.getNickName())) {
            User user = userRepository.save(authUser);
            return UserDto.Res.of(user);
        }else{
            throw new IllegalArgumentException("닉네임 중복을 다시 확인해주세요.");
        }
    }

    @Transactional
    public UserDto.Res webJoin(UserDto.Req req) {
        UserDto.Res join = join(req);

        AreaDto.Req area = new AreaDto.Req();
        area.setCity("서울특별시");
        area.setGuGun("강남구");

        areaService.addArea(join.getUid(), area);

        return join;
    }

    public void deleteMember(String uid) {
        userRepository.deleteById(uid);
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

    public UserDto.SimpleRes getSimpleUser(String uId) {
        User byUid = userRepository.findByUid(uId);
        return UserDto.SimpleRes.of(byUid);
    }
}
