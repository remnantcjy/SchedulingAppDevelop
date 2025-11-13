package org.example.schedulingappdevelop.user.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
}
