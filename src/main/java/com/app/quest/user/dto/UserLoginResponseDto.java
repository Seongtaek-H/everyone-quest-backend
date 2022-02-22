package com.app.quest.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
@AllArgsConstructor
public class UserLoginResponseDto {

    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("access_token")
    private String accessToken;
}
