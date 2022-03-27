package server.sookdak.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    SIGNUP_SUCCESS(OK, "회원가입을 완료했습니다."),
    LOGIN_SUCCESS(OK, "로그인에 성공했습니다."),
    USER_INFO_SUCCESS(OK, "유저 조회에 성공했습니다."),
    REISSUE_SUCCESS(OK, "토큰 재발급에 성공했습니다.");

    private final HttpStatus status;
    private final String message;
}
