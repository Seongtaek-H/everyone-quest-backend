package com.app.quest.user.exception;

import com.app.quest.error.ErrorCode;
import com.app.quest.error.exception.BusinessException;

import lombok.Getter;

@Getter
public class EmailDuplicateException extends BusinessException {

    public EmailDuplicateException(String message, ErrorCode errorCode){
        super(message, errorCode);
    }
}
