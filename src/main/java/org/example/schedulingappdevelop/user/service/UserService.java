package org.example.schedulingappdevelop.user.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.config.PasswordMismatchException;
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

    // 회원가입
    @Transactional
    public SignupUserResponse signup(SignupUserRequest request) {

        // 유저 객체 생성
        User user = new User(request.getName(), request.getEmail(), request.getPassword());

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
                () -> new IllegalStateException("없는 유저입니다.")
        );


        // 비밀번호 확인
        if (!request.getPassword().equals(user.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 틀렸습니다.");
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
                () -> new IllegalStateException("없는 유저입니다.")
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
                () -> new IllegalStateException("없는 유저입니다.")
        );

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
    public void delete(Long userId) {
        // 해당 id의 유저 있는지 확인
        boolean existence = userRepository.existsById(userId);

        // 없으면 예외 던지기
        if (!existence) {
            throw new IllegalStateException("없는 유저입니다.");
        }

        // 있으면 해당 id로 유저 삭제하기
        userRepository.deleteById(userId);
    }


}
