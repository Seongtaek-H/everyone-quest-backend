package com.app.quest.user;

import com.app.quest.user.dto.UserLoginRequestDto;
import com.app.quest.user.dto.UserLoginResponseDto;
import com.app.quest.user.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity joinUser(@RequestBody @Valid UserSignupDto request){
        userService.join(request);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody @Valid UserLoginRequestDto request){
        System.out.printf("@@@@@@@@@@");
        UserLoginResponseDto userLoginResponseDto = this.userService.loginMember(request);
        return new ResponseEntity<>(userLoginResponseDto,HttpStatus.OK);
    }

    @GetMapping("/test")
    public String hello(){
        return "hello";
    }
}
