package server.sookdak.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
}
