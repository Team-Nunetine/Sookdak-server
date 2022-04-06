package server.sookdak.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import server.sookdak.dto.BaseResponse;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<BaseResponse> handleCustomException(CustomException e, HttpServletRequest request) {
        log.warn(String.format("[%s Error] : %s %s", e.getExceptionCode().getStatus(), request.getMethod(), request.getRequestURI()));
        return BaseResponse.toCustomErrorResponse(e.getExceptionCode());
    }

    // @RequestBody valid 에러
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<BaseResponse> handleMethodArgNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn(String.format("[400 Error] : %s %s", request.getMethod(), request.getRequestURI()));
        return BaseResponse.toBasicErrorResponse(HttpStatus.BAD_REQUEST, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    // @ModelAttribute valid 에러
    @ExceptionHandler(value = { BindException.class })
    protected ResponseEntity<BaseResponse> handleMethodArgNotValidException(BindException e, HttpServletRequest request) {
        log.warn(String.format("[400 Error] : %s %s", request.getMethod(), request.getRequestURI()));
        return BaseResponse.toBasicErrorResponse(HttpStatus.BAD_REQUEST, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
