package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.User;
import com.codesoom.sejongdeveloper.errors.UserNotFoundException;
import com.codesoom.sejongdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public User login(String loginId, String password) {
        User user = userRepository.findByLoginIdAndPassword(loginId, password)
                .orElseThrow(() -> new UserNotFoundException(loginId));

        return user;
    }

}
