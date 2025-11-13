package org.example.schedulingappdevelop.user.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.user.dto.*;
import org.example.schedulingappdevelop.user.entity.User;
import org.example.schedulingappdevelop.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        // 유저 객체 생성
        User user = new User(request.getName(), request.getEmail());

        // 유저 객체 저장소에 저장
        User savedUser = userRepository.save(user);

        // 반환
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getAll() {
        // 유저 저장소에 있는 모든 유저 - 리스트로 반환
        List<User> userList = userRepository.findAll();

        // 반환 dtos 리스트 생성
        List<GetUserResponse> dtos = new ArrayList<>();

        // 리스트 데이터타입: User -> GetUserResponse로 변환 후 반환
        return userList.stream().map(
                user -> new GetUserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail()
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
                user.getEmail()
        );
    }

    @Transactional
    public UpdateUserResponse update(UpdateUserRequest request) {
        // 해당 id의 유저가 있는지 확인 / 예외처리
        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

        // 유저의 이름을 변경
        user.update(request.getName());

        // 수정된 유저 반환
        return new UpdateUserResponse(user.getId());
    }
}
