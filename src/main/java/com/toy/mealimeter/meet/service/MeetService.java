package com.toy.mealimeter.meet.service;

import com.toy.mealimeter.common.exception.NotMatchingMeetException;
import com.toy.mealimeter.meet.domain.ApplyUser;
import com.toy.mealimeter.meet.domain.Meet;
import com.toy.mealimeter.meet.dto.MeetDto;
import com.toy.mealimeter.meet.repository.MeetRepository;
import com.toy.mealimeter.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;

    @Transactional
    public MeetDto.Res createMeet(User user, MeetDto.Req req) {

        Meet meet = req.toEntity(user);

        if (user.meetCheck(meet)) {
            meet.addEnterUser(user);
            return MeetDto.Res.of(meetRepository.save(meet));
        }else{
            throw new NotMatchingMeetException();
        }
    }

    public List<MeetDto.SimpleRes> getMeetList(User user, Pageable pageable) {
        return MeetDto.SimpleRes.listOf(meetRepository.findByMeetArea(user.getActiveArea(), pageable));
    }

    @Transactional
    public void addApplyList(User user, long meetId) {
        Meet meet = meetRepository.findById(meetId).orElseThrow(IllegalArgumentException::new);

        if (user.meetCheck(meet)) {
            meet.addApplyUser(user);
        }else{
            throw new NotMatchingMeetException();
        }
    }

    @Transactional
    public void addEnterList(User user, long meetId) {

        Meet meet = meetRepository.findById(meetId).orElseThrow(IllegalArgumentException::new);

        if (user.meetCheck(meet)) {
            meet.addEnterUser(user);
        }else{
            throw new NotMatchingMeetException();
        }

    }

}
