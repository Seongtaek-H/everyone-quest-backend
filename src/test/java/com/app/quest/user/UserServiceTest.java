package com.app.quest.user;

import com.app.quest.user.dto.SignupUserDto;
import com.app.quest.user.exception.EmailDuplicateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 정보 이메일 중복 저장")
    public void 유저_정보_이메일_중복_저장() {
        String email = "al@naver.com";
        String name = "minho";
        String password = "1234";
        String phoneNumber = "000-000-000";

        SignupUserDto dto1 = SignupUserDto.builder().email(email).username(name).password(password).phoneNumber(phoneNumber).build();
        SignupUserDto dto2 = SignupUserDto.builder().email(email).username(name).password(password).phoneNumber(phoneNumber).build();

        userService.join(dto1);

        org.assertj.core.api.Assertions.assertThatExceptionOfType(EmailDuplicateException.class)
                .isThrownBy(()->{
                    userService.join(dto2);
                });
    }

    @Test
    @DisplayName("유저 정보 암호화 저장")
    public void 유저_정보_암호화_저장(){
        String email = "al@naver.com";
        String name = "minho";
        String password = "1234";
        String phoneNumber = "000-000-000";

        SignupUserDto dto = SignupUserDto.builder().email(email).username(name).password(password).phoneNumber(phoneNumber).build();
        userService.join(dto);

        User find = userRepository.findByEmail(dto.getEmail());

        Assertions.assertTrue(bCryptPasswordEncoder.matches(password,find.getPassword()));
    }
}
