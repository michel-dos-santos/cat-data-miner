package br.com.itau.application.rest.exception;

import br.com.itau.domain.exception.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(APIException.class)
    public ResponseEntity<Object> handleApiException(APIException ex, WebRequest request){
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.valueOf(ex.getStatusCode()), request );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        String code = status.getReasonPhrase();
        String description = ex.getClass().getSimpleName();
        String message = "";

        if (ex instanceof APIException) {
            description = ((APIException) ex).getReason();
            message = ex.getMessage();

        }

        ErrorResponse errorResponse = new ErrorResponse(code, description, message);
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

}