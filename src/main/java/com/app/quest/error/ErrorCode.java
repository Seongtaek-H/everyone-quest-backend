package com.app.quest.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 에러 코드는 enum 타입으로 한 곳에서 관리합니다.
 * 에러 코드가 전체적으로 흩어져있을 경우 코드,
 * 메시지의 중복을 방지하기 어렵고 전체적으로 관리하는 것이 매우 어렵습니다.
 * C001 같은 코드도 동일하게 enum으로 관리 하는 것도 좋습니다.
 * 에러 메시지는 Common과 각 도메인별로 관리하는 것이 효율적일 거 같습니다.
 * */

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),


    // User
    EMAIL_DUPLICATION(400, "U001", "Email is duplication"),
    NOT_EXIST_USER(400, "U002", "User does not exist"),
    NOT_VALID_PASSWORD(400, "U003", "The password is not valid"),

    ;

    private int status;
    private final String code;
    private final String message;
}