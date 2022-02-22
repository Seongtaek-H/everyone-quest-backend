package com.app.quest.user;


import com.app.quest.common.BaseEntity;
import com.app.quest.user.dto.UserSignupDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {


    @Column(name="email", unique = true)
    private String email;

    @Column(name="phone_number", unique = true)
    private String phoneNumber;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="home_location")
    private String homeLocation;

    @Column(name="picture_url")
    private String pictureUrl;

    @Column(name="coupon_count")
    private String couponCount;




    //---------------------//
    @Builder
    public User(
            String email,
            String password,
            String username,
            String phoneNumber) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public static User toEntity(UserSignupDto user){
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .build();
    }
}
