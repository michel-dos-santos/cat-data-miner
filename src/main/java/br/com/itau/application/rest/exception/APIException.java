package br.com.itau.application.rest.exception;

import br.com.itau.domain.exception.ErrorResponse;
import io.micrometer.core.annotation.Counted;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class APIException extends Exception {

    private final String code;
    private final String reason;
    private final Integer statusCode;

    public APIException(String code, String reason, String message, Integer statusCode) {
        super(message);
        this.code = code;
        this.reason = reason;
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public static APIException badRequest(String reason, String message) {
        return new APIException(HttpStatus.BAD_REQUEST.getReasonPhrase(), reason, message,
                HttpStatus.BAD_REQUEST.value());
    }

    public static APIException unauthorized(String reason, String message) {
        return new APIException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), reason, message,
                HttpStatus.UNAUTHORIZED.value());
    }

    public static APIException forbidden(String reason, String message) {
        return new APIException(HttpStatus.FORBIDDEN.getReasonPhrase(), reason, message, HttpStatus.FORBIDDEN.value());
    }

    public static APIException notFound(String reason, String message) {
        return new APIException(HttpStatus.NOT_FOUND.getReasonPhrase(), reason, message, HttpStatus.NOT_FOUND.value());
    }

    public static APIException internalError(String reason, String message) {
        return new APIException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), reason, message,
                HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static APIException getBy(ErrorResponse errorResponse) {
        String code = errorResponse.getCode();
        String description = errorResponse.getDescription();
        String message = errorResponse.getMessage();
        HttpStatus httpStatus = Arrays.stream(HttpStatus.values()).filter(v -> code.equalsIgnoreCase(v.getReasonPhrase())).findFirst().get();

        return new APIException(httpStatus.getReasonPhrase(), description, message, httpStatus.value());
    }
}