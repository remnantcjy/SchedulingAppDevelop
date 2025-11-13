package org.example.schedulingappdevelop.user.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.user.dto.CreateUserRequest;
import org.example.schedulingappdevelop.user.dto.CreateUserResponse;
import org.example.schedulingappdevelop.user.entity.User;
import org.example.schedulingappdevelop.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
