package server.sookdak.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import server.sookdak.constants.ExceptionCode;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ExceptionCode exceptionCode;
}
