package com.app.quest.user;

import com.app.quest.common.BaseController;
import com.app.quest.common.JwtProvider;
import com.app.quest.user.dto.UserLoginRequestDto;
import com.app.quest.user.dto.UserSignupDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserControllerTest extends BaseController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

//    @BeforeEach
//    public void before(){
//        userRepository.deleteAll();
//    }

    @Test
    public void 회원가입_성공() throws Exception{
        String email = "alsgh@naver.com";
        String username = "minho";
        String password = "1234abcd";
        String phoneNumber = "010-0010-0000";

        UserSignupDto dto = UserSignupDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber).build();

        mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))

        ).andDo(print())
                .andDo(document("signup-success",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("컨텐츠 타입 헤더")
                        ),
                        requestFields(
                                fieldWithPath("email").description("회원 이메일"),
                                fieldWithPath("password").description("회원 비밀번호"),
                                fieldWithPath("username").description("회원 이름"),
                                fieldWithPath("phoneNumber").description("회원 휴대폰 번호")
                        )
                ))
                .andExpect(status().isCreated());
    }

    @Test
    public void 회원가입_실패_이메일_공백() throws Exception{
        String email = "";
        String username = "minho";
        String password = "1234abcd";
        String phoneNumber = "010-0010-0000";

        UserSignupDto dto = UserSignupDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber).build();

        mockMvc.perform(
                        post("/api/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(dto))
                ).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입_실패_유효하지않은_이메일() throws Exception{
        String email = "a";
        String username = "minho";
        String password = "1234abcd";
        String phoneNumber = "010-0010-0000";

        UserSignupDto dto = UserSignupDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber).build();

        mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
        ).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입_실패_유저이름_공백() throws Exception{
        String email = "alsgh458@gmail.com";
        String username = "";
        String password = "1234abcd";
        String phoneNumber = "010-0010-0000";

        UserSignupDto dto = UserSignupDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber).build();

        mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
        ).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입_실패_비밀번호_공백() throws Exception{
        String email = "alsgh458@gmail.com";
        String username = "alsgh458";
        String password = "";
        String phoneNumber = "010-0010-0000";

        UserSignupDto dto = UserSignupDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber).build();

        mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
        ).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입_실패_길이가_짧은_비밀번호() throws Exception{
        String email = "alsgh458@gmail.com";
        String username = "alsgh458";
        String password = "1a";
        String phoneNumber = "010-0010-0000";

        UserSignupDto dto = UserSignupDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber).build();

        mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
        ).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입_실패_문자가_없는_비밀번호() throws Exception{
        String email = "alsgh458@gmail.com";
        String username = "alsgh458";
        String password = "123333";
        String phoneNumber = "010-0010-0000";

        UserSignupDto dto = UserSignupDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber).build();

        mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
        ).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입_실패_최대길이를_초과한_비밀번호() throws Exception{
        String email = "alsgh458@gmail.com";
        String username = "alsgh458";
        String password = "1bd111111111111111111111111111111111";
        String phoneNumber = "010-0010-0000";

        UserSignupDto dto = UserSignupDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber).build();

        mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
        ).andDo(print()).andExpect(status().isBadRequest());
    }


    @Test
    public void 회원가입_실패_휴대폰번호_공백() throws Exception{
        String email = "alsgh458@gmail.com";
        String username = "alsgh458";
        String password = "1234abcd";
        String phoneNumber = "";

        UserSignupDto dto = UserSignupDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber).build();

        mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto))
        ).andDo(print()).andExpect(status().isBadRequest());
    }


    @Test
    public void 로그인_성공() throws Exception{
        UserLoginRequestDto dto =  createTestAccount();
        System.out.printf(""+dto);
        mockMvc.perform(
                        post("/api/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(dto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("user_email").exists())
                .andExpect(jsonPath("access_token").exists());
    }



}
