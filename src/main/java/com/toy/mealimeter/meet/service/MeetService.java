package com.toy.mealimeter.meet.service;

import com.toy.mealimeter.common.exception.NoUserException;
import com.toy.mealimeter.common.exception.NotMatchingMeetException;
import com.toy.mealimeter.common.exception.NotMeetMasterException;
import com.toy.mealimeter.meet.domain.ApplyUser;
import com.toy.mealimeter.meet.domain.EnterStatus;
import com.toy.mealimeter.meet.domain.EnterUser;
import com.toy.mealimeter.meet.domain.Meet;
import com.toy.mealimeter.meet.dto.ApplyUserDto;
import com.toy.mealimeter.meet.dto.EnterUserDto;
import com.toy.mealimeter.meet.dto.MeetDto;
import com.toy.mealimeter.meet.repository.EnterUserRepository;
import com.toy.mealimeter.meet.repository.MeetRepository;
import com.toy.mealimeter.user.domain.User;
import com.toy.mealimeter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final EnterUserRepository enterUserRepository;
    private final UserRepository userRepository;

    @Transactional
    public MeetDto.Res createMeet(User user, MeetDto.Req req) {

        Meet meet = req.toEntity(user);

        if (user.meetCheck(meet)) {
            meet.addEnterUser(user);
            return MeetDto.Res.of(meetRepository.save(meet));
        } else {
            throw new NotMatchingMeetException();
        }
    }

    public List<MeetDto.SimpleRes> getMeetList(User user, Pageable pageable) {
        List<Meet> meetList = meetRepository.findByMeetArea(user.getActiveArea(), pageable).stream().filter(user::meetCheck).collect(Collectors.toList());
        return MeetDto.SimpleRes.listOf(meetList);
    }

    public MeetDto.Res getMeet(User user) {
        EnterUser enterUser = enterUserRepository.findTopByUserAndEnterStatus(user, EnterStatus.Enter);
        if (enterUser != null) {
            return MeetDto.Res.of(enterUser.getMeet());
        }else{
            return null;
        }
    }

    @Transactional
    public void addApplyList(User user, long meetId) {
        Meet meet = meetRepository.findById(meetId).orElseThrow(IllegalArgumentException::new);

        if (user.meetCheck(meet)) {
            meet.addApplyUser(user);
        } else {
            throw new NotMatchingMeetException();
        }
    }

    @Transactional
    public void removeApplyList(ApplyUser user, long meetId) {
        Meet meet = meetRepository.findById(meetId).orElseThrow(IllegalArgumentException::new);
        meet.removeApplyUser(user);
    }

    @Transactional
    public void addEnterList(User master, long meetId, String userId) {

        Meet meet = meetRepository.findById(meetId).orElseThrow(IllegalArgumentException::new);

        if (meetMasterCheck(master, meet)) {

            User user = userRepository.findByUid(userId);
            ApplyUser applyUser = meet.getApplyUserList().stream().filter(apply -> apply.getUser().equals(user)).findFirst().orElseThrow(NoUserException::new);

            if (user.meetCheck(meet)) {
                meet.addEnterUser(applyUser.getUser());
                meet.removeApplyUser(applyUser);
            } else {
                throw new NotMatchingMeetException();
            }
        }else{
            throw new NotMatchingMeetException();
        }
    }

    public List<ApplyUserDto.Res> getApplyList(User user, long meetId) {

        Meet meet = meetRepository.findById(meetId).orElse(null);

        if (meet != null) {
            if (meetMasterCheck(user, meet)) {
                return ApplyUserDto.Res.listOf(meet.getApplyUserList());
            } else {
                throw new NotMeetMasterException();
            }
        }else{
            return null;
        }
    }

    public List<EnterUserDto.Res> getEnterList(User user, long meetId) {
        Meet meet = meetRepository.findById(meetId).orElseThrow(IllegalArgumentException::new);
        return EnterUserDto.Res.listOf(meet.getEnterUserList());
    }

    public Boolean meetMasterCheck(User user, Meet meet) {
        return meet.getMeetMaster().equals(user);
    }
}
