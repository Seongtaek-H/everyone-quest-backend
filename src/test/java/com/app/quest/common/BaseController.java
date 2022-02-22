package com.app.quest.common;

import com.app.quest.user.User;
import com.app.quest.user.UserService;
import com.app.quest.user.dto.UserLoginRequestDto;
import com.app.quest.user.dto.UserSignupDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsAutoConfiguration.class)
@ActiveProfiles("test")
@Transactional
public abstract class BaseController {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserService userService;

    protected UserLoginRequestDto createTestAccount() {

        String email = "alsgh458@gmail.com";
        String username = "alsgh458";
        String password = "1234abcd";
        String phoneNumber = "010-0010-0000";

        UserSignupDto dto = UserSignupDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber).build();

        userService.join(dto);
        UserLoginRequestDto reqDto = new UserLoginRequestDto(email, password);
        return reqDto;
    }

}
