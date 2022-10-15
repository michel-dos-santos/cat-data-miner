package br.com.itau.application.rest.exception;

import org.springframework.http.HttpStatus;

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

    public static APIException notFound(String reason, String message) {
        return new APIException(HttpStatus.NOT_FOUND.getReasonPhrase(), reason, message, HttpStatus.NOT_FOUND.value());
    }

    public static APIException internalError(String reason, String message) {
        return new APIException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), reason, message,
                HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}