package dev.sangco.hospital.common;

import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ErrorResponse {

    private String code;

    private String message;

    private List<FieldError> FieldErrors;

    @Data
    private static class FieldError {

        private String field;

        private String message;
    }

    public static ErrorResponse createErrorResponse(BindingResult bindingResult) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("badRequest");
        errorResponse.setCode("잘못된 요청입니다.");
        errorResponse.setFieldErrors(bindingResult.getFieldErrors().stream().map(error -> {
            ErrorResponse.FieldError FieldError = new ErrorResponse.FieldError();
            FieldError.setField(error.getField());
            FieldError.setMessage(error.getDefaultMessage());
            return FieldError;
        }).collect(Collectors.toList()));
        return errorResponse;
    }

}





