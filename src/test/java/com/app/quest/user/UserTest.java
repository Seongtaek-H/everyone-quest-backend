package com.app.quest.user;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class UserTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void cleanup(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 정보 저장 테스트")
    public void 유저_정보_저장_테스트(){
        //Given
        String email = "al@naver.com";
        String name = "minho";
        String password = "1234";
        String phoneNumber = "000-000-000";
        User user = User.builder().username(name).email(email).password(password).phoneNumber(phoneNumber).build();

        //When
        User saved = userRepository.save(user);
        //Then
        Assertions.assertEquals(user.getUsername(), saved.getUsername());
        Assertions.assertEquals(user.getEmail(), saved.getEmail());
        Assertions.assertEquals(user.getPassword(), saved.getPassword());
        Assertions.assertEquals(user.getPhoneNumber(), saved.getPhoneNumber());

        Assertions.assertNull(user.getPictureUrl());
        Assertions.assertNull(user.getCouponCount());
        Assertions.assertNull(user.getHomeLocation());

        Assertions.assertNotNull(user.getCreatedAt());
        Assertions.assertNotNull(user.getModifiedAt());
        Assertions.assertNotNull(user.getId());
    }



}
