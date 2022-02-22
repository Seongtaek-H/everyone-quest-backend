package com.app.quest.user;

import com.app.quest.common.JwtProvider;
import com.app.quest.error.ErrorCode;
import com.app.quest.user.dto.UserLoginRequestDto;
import com.app.quest.user.dto.UserLoginResponseDto;
import com.app.quest.user.dto.UserSignupDto;
import com.app.quest.user.exception.EmailDuplicateException;
import com.app.quest.user.exception.LoginFailureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;


    @Transactional
    public void join(UserSignupDto request) throws EmailDuplicateException {
        checkDuplicatedEmail(request.getEmail());
        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(User.toEntity(request));
    }

    private void checkDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicateException("Email duplicated", ErrorCode.EMAIL_DUPLICATION);
        }
    }

    public UserLoginResponseDto loginMember(UserLoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new LoginFailureException("Not exist user", ErrorCode.NOT_EXIST_USER));
        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new LoginFailureException("Invalid password", ErrorCode.NOT_VALID_PASSWORD);
        return new UserLoginResponseDto(user.getEmail(), jwtProvider.createToken(request.getEmail()));
    }
}
