package org.example.schedulingappdevelop.user.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.common.config.Exception.UserNotFoundException;
import org.example.schedulingappdevelop.common.config.auth.PasswordEncoder;
import org.example.schedulingappdevelop.common.config.Exception.PasswordMismatchException;
import org.example.schedulingappdevelop.common.config.Exception.UserHasScheduleException;
import org.example.schedulingappdevelop.schedule.entity.Schedule;
import org.example.schedulingappdevelop.schedule.repository.ScheduleRepository;
import org.example.schedulingappdevelop.user.dto.*;
import org.example.schedulingappdevelop.user.entity.User;
import org.example.schedulingappdevelop.user.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ScheduleRepository scheduleRepository;

    // 회원가입
    @Transactional
    public SignupUserResponse signup(SignupUserRequest request) {

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(request.getPassword());

        // 유저 객체 생성
        User user = new User(request.getName(), request.getEmail(), encodePassword);

        // 유저 객체 저장소에 저장
        User savedUser = userRepository.save(user);

        // 반환
        return new SignupUserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }


    // 로그인
    @Transactional(readOnly = true)
    public SessionUser login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );

        // 암호화 된 비밀번호와 request.getPassword()가 일치하는지 확인
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        // 비밀번호 확인
        if (!passwordMatches) {
            throw new PasswordMismatchException("이메일 또는 비밀번호가 올바르지 않습니다.");
        } else {
            System.out.println("로그인 성공!");
        }

        return new SessionUser(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getAll() {
        // 유저 저장소에 있는 모든 유저 - 리스트로 반환 (수정일 기준으로 내림차순 정렬)
        List<User> userList = userRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedAt"));

        // 반환 dtos 리스트 생성
        List<GetUserResponse> dtos = new ArrayList<>();

        // 리스트 데이터타입: User -> GetUserResponse로 변환 후 반환
        return userList.stream().map(
                user -> new GetUserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                )).toList();
    }

    @Transactional(readOnly = true)
    public GetUserResponse getOne(Long userId) {
        // 해당 userId의 유저 있는지 조회 / 예외처리
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );

        // 해당 user 반환
        return new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        // 해당 id의 유저가 있는지 확인 / 예외처리
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );

        // 암호화 된 비밀번호와 request.getPassword()가 일치하는지 확인
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        // user의 비밀번호 불일치 시 - 이름 수정 x
        // 비밀번호 불일치 예외 사용 !
        if (!passwordMatches) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않으므로 회원정보를 수정할 수 없습니다.");
        }

        // 유저의 이름을 변경
        user.update(request.getName());

        // 수정된 유저 반환
        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public void delete(DeleteUserRequest request, Long userId) {

        // 해당 id의 유저 있는지 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );

        // 유저 있을 때 -> 비밀번호 확인
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        // 비밀번호 불일치 시, 예외 처리
        if (!passwordMatches) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않아 회원 탈퇴가 불가능합니다.");
        }

        // 비밀번호 일치 시, 해당 유저 - 게시글이 있는지 확인
        List<Schedule> scheduleList = scheduleRepository.findByUserId(userId);

        // 게시글이 존재할 시, 예외 처리
        if (!scheduleList.isEmpty()) {
            throw new UserHasScheduleException("해당 유저가 작성한 게시글이 남아 있어 삭제할 수 없습니다. 게시글을 모두 삭제한 후 다시 시도해주세요.");
        } else {
            // 게시글이 없을 시, 해당 유저 삭제
            userRepository.deleteById(userId);
        }
    }

}
