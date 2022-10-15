package br.com.itau.domain.exception;

public class ErrorResponse {

    private String code;
    private String description;
    private String message;

    public ErrorResponse(String code, String description, String message) {
        this.code = code;
        this.description = description;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
    }

}
