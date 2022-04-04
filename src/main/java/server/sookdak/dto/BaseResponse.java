package server.sookdak.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.ExceptionCode;
import server.sookdak.constants.SuccessCode;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BaseResponse {

    private Boolean success;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public BaseResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static BaseResponse of(Boolean success, String message) {
        return new BaseResponse(success, message);
    }

    public static ResponseEntity<BaseResponse> toCustomErrorResponse(ExceptionCode exceptionCode) {
        return ResponseEntity
                .status(exceptionCode.getStatus())
                .body(BaseResponse.of(false, exceptionCode.getMessage()));
    }

    public static ResponseEntity<BaseResponse> toBasicErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(BaseResponse.of(false, message));
    }

    public static ResponseEntity<BaseResponse> toSuccessResponse(SuccessCode successCode) {
        return ResponseEntity
                .status(successCode.getStatus())
                .body(BaseResponse.of(true, successCode.getMessage()));
    }
}
