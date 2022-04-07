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
    REISSUE_SUCCESS(OK, "토큰 재발급에 성공했습니다."),

    BOARD_SAVE_SUCCESS(OK, "게시판 추가에 성공했습니다."),
    BOARD_READ_SUCCESS(OK, "게시판 조회에 성공했습니다."),

    POST_SAVE_SUCCESS(OK, "게시글 저장에 성공했습니다."),
    POST_LIST_READ_SUCCESS(OK, "게시글 목록 조회에 성공했습니다."),

    STAR_SUCCESS(OK, "게시판 즐겨찾기에 성공했습니다."),
    STAR_CANCEL_SUCCESS(OK, "게시판 즐겨찾기 취소에 성공했습니다.");

    private final HttpStatus status;
    private final String message;
}
