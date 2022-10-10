package br.com.itau.domain.exception;

public class ServiceException extends RuntimeException {

    private ErrorResponse errorResponse;

    public ServiceException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}