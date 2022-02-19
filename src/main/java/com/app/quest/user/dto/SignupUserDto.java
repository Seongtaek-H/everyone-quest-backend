package com.app.quest.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
@AllArgsConstructor
public class SignupUserDto {

    @Email
    @NotBlank(message="이메일은 필수 입력 값입니다.")
    private String email;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,20}",
            message = "비밀번호는 영문 대,소문자와 숫자가 적어도 1개 이상씩 포함된 6자 ~ 20자의 비밀번호여야 합니다.")
    @NotBlank(message="비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message="유저 이름은 필수 입력 값입니다.")
    private String username;

    @NotBlank(message="휴대폰 번호는 필수 입력 값입니다.")
    private String phoneNumber;
}
