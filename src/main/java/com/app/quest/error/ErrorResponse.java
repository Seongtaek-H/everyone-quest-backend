package com.app.quest.error;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Error Response 객체는 항상 동일한 Error Response를 가져야 합니다.
 * 그렇지 않으면 클라이언트에서 예외 처리를 항상 동일한 로직으로 처리하기 어렵습니다.
 * Error Response 객체를 유연하게 처리하기 위해서
 * 간혹 Map<Key, Value>형식으로 처리하는데 이는 좋지 않다고 생각합니다.
 *
 *우선 Map 이라는 친구는 런타입시에 정확한 형태를 갖추기 때문에 객체를 처리하는
 * 개발자들도 정확히 무슨 키에 무슨 데이터가 있는지 확인하기 어렵습니다.
 *
 * message : 에러에 대한 message를 작성합니다.
 * status : http status code를 작성합니다. header 정보에도 포함된 정보이니 굳이 추가하지 않아도 됩니다.
 * errors : 요청 값에 대한 field, value, reason 작성합니다.
 *          일반적으로 @Valid 어노테이션으로 JSR 303: Bean Validation에 대한 검증을 진행 합니다.
 *          만약 errors에 바인인된 결과가 없을 경우 null이 아니라 빈 배열 []을 응답해줍니다.
 *          null 객체는 절대 리턴하지 않습니다. null이 의미하는 것이 애매합니다.
 * code :   에러에 할당되는 유니크한 코드값입니다.
 * */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private int status;
    private List<FieldError> errors;
    private String code;

    private ErrorResponse(final ErrorCode code, final List<FieldError> errors){
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = errors;
    }

    private ErrorResponse(final ErrorCode code){
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
        return new ErrorResponse(code, errors);
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError{
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult){
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "": error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }


}