package application.exception;

import application.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { UserException.class })
    protected ResponseEntity handleConflict(UserException ex, WebRequest request) {

        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, errorDto,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}
