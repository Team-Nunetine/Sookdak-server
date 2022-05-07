package server.sookdak.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    /* 400 - 잘못된 요청 */
    WRONG_TYPE_ORDER(BAD_REQUEST, "잘못된 order type 입니다."),

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
    LIKE_DENIED(FORBIDDEN, "본인이 쓴 글은 공감할 수 없습니다."),
    WRITER_ONLY_EDIT(FORBIDDEN, "본인이 쓴 글만 수정할 수 있습니다."),
    WRITER_ONLY_DELETE(FORBIDDEN, "본인이 쓴 글만 삭제할 수 있습니다."),
    CREATOR_ONLY_DELETE(FORBIDDEN, "본인이 만든 게시판만 삭제할 수 있습니다."),
    SCRAP_DENIED(FORBIDDEN, "본인이 쓴 글은 스크랩할 수 없습니다."),
    RE_COMMENT_ONLY(FORBIDDEN, "댓글은 답글까지만 달 수 있습니다."),

    /* 404 - 찾을 수 없는 리소스 */
    MEMBER_EMAIL_NOT_FOUND(NOT_FOUND, "가입되지 않은 이메일입니다."),
    BOARD_NOT_FOUND(NOT_FOUND, "게시판을 찾을 수 없습니다."),
    POST_NOT_FOUND(NOT_FOUND, "게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(FORBIDDEN, "댓글을 찾을 수 없습니다."),

    /* 409 - 중복된 리소스 */
    DUPLICATE_BOARD_NAME(CONFLICT, "이미 해당 이름을 가진 게시판이 있습니다.");





    private final HttpStatus status;
    private final String message;
}
