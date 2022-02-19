package com.app.quest.user;

import com.app.quest.error.ErrorCode;
import com.app.quest.user.dto.SignupUserDto;
import com.app.quest.user.exception.EmailDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public Long join(SignupUserDto request) throws EmailDuplicateException {
        // 중복 검사
        checkDuplicatedEmail(request.getEmail());

        String cryptPwd = bCryptPasswordEncoder.encode(request.getPassword());
        request.setPassword(cryptPwd);

        User user = User.toEntity(request);
        User saved = userRepository.save(user);
        return saved.getId();
    }

    private void checkDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicateException("Email duplicated", ErrorCode.EMAIL_DUPLICATION);
        }
    }
}
