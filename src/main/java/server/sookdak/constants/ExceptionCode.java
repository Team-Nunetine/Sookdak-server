package server.sookdak.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    /* 400 - 잘못된 요청 */

    /* 401 - 인증 실패 */
    // token 관련
    WRONG_TYPE_TOKEN(UNAUTHORIZED, "잘못된 JWT 서명을 가진 토큰입니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    WRONG_TOKEN(UNAUTHORIZED, "JWT 토큰이 잘못되었습니다."),
    UNKNOWN_ERROR(UNAUTHORIZED, "알 수 없는 요청 인증 에러! 헤더에 토큰을 넣어 보냈는지 다시 한 번 확인해보세요."),
    ACCESS_DENIED(UNAUTHORIZED, "접근이 거절되었습니다."),
    USER_NOT_FOUND(UNAUTHORIZED, "로그인 유저 정보가 없습니다.")

    /* 403 - 허용되지 않은 접근 */,
    FORBIDDEN_ACCESS(FORBIDDEN, "허용되지 않은 접근입니다."),
    SOOKMYUNG_ONLY(FORBIDDEN, "숙명 계정으로 로그인해야 합니다."),
    IMAGE_NUM_EXCESS(FORBIDDEN, "이미지 개수가 10개를 초과했습니다."),

    /* 404 - 찾을 수 없는 리소스 */
    MEMBER_EMAIL_NOT_FOUND(NOT_FOUND, "가입되지 않은 이메일입니다."),
    BOARD_NOT_FOUND(NOT_FOUND, "게시판을 찾을 수 없습니다."),

    /* 409 - 중복된 리소스 */
    DUPLICATE_BOARD_NAME(CONFLICT, "이미 해당 이름을 가진 게시판이 있습니다.");

    private final HttpStatus status;
    private final String message;
}
