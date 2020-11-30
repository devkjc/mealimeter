package com.toy.mealimeter.meet.service;

import com.toy.mealimeter.meet.domain.ApplyUser;
import com.toy.mealimeter.meet.domain.Meet;
import com.toy.mealimeter.meet.dto.MeetDto;
import com.toy.mealimeter.meet.repository.MeetRepository;
import com.toy.mealimeter.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;

    public MeetDto.Res createMeet(User user, MeetDto.Req req) {

        return MeetDto.Res.of(req.toEntity(user));
    }

    public ApplyUser toEntity(Meet meet, User user) {
        return ApplyUser.builder()
                .meet(meet)
                .user(user)
                .build();
    }

}
