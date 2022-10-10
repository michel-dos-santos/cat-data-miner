package br.com.itau.application.rest.exception;

import br.com.itau.domain.exception.ErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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

        } else if(ex instanceof MethodArgumentNotValidException){
            StringBuilder messageBuilder = new StringBuilder();
            BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
            List<FieldError> fieldErrors = result.getFieldErrors();

            fieldErrors.forEach(error -> {
                messageBuilder.append("Erro no campo: ").append(error.getField())
                        .append(" - mensagem de erro: ").append(error.getDefaultMessage());
                if(fieldErrors.indexOf(error) < (fieldErrors.size() -1)){
                    messageBuilder.append(" | ");
                }
            });

            description = messageBuilder.toString();
        } else if(ex instanceof ConstraintViolationException) {
            StringBuilder messageBuilder = new StringBuilder();
            AtomicInteger index = new AtomicInteger(0);
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) ex).getConstraintViolations();

            constraintViolations.forEach(violation -> {
                String field = substringAfter(violation.getPropertyPath().toString(), ".");
                messageBuilder.append("Erro no campo: ").append(field).append(" - mensagem de erro: ").append(violation.getMessage());
                if (index.getAndIncrement() < constraintViolations.size() - 1) {
                    messageBuilder.append(" | ");
                }
            });

            description = messageBuilder.toString();
        }

        ErrorResponse errorResponse = new ErrorResponse(code, description, message);
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    private String substringAfter(String str, String separator) {
        if (StringUtils.isEmpty(str)) {
            return str;
        } else if (separator == null) {
            return "";
        } else {
            int pos = str.indexOf(separator);
            return pos == -1 ? "" : str.substring(pos + separator.length());
        }
    }
}