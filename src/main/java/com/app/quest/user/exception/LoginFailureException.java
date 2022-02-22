package com.app.quest.user.exception;

import com.app.quest.error.ErrorCode;
import com.app.quest.error.exception.BusinessException;

public class LoginFailureException extends BusinessException {

    public LoginFailureException(String message, ErrorCode errorCode){
        super(message, errorCode);
    }
}
