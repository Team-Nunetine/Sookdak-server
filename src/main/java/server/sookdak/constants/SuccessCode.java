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
    BOARD_DELETE_SUCCESS(OK, "게시판 삭제에 성공했습니다."),

    POST_SAVE_SUCCESS(OK, "게시글 저장에 성공했습니다."),
    POST_EDIT_SUCCESS(OK, "게시글 수정에 성공했습니다."),
    POST_DETAIL_READ_SUCCESS(OK, "게시글 상세 조회에 성공했습니다."),
    POST_LIST_READ_SUCCESS(OK, "게시글 목록 조회에 성공했습니다."),
    POST_DELETE_SUCCESS(OK, "게시글 삭제에 성공했습니다."),
    POST_WARN_SUCCESS(OK, "게시글 신고에 성공했습니다."),
    POST_SEARCH_SUCCESS(OK, "게시글 검색에 성공했습니다."),

    STAR_SUCCESS(OK, "게시판 즐겨찾기에 성공했습니다."),
    STAR_CANCEL_SUCCESS(OK, "게시판 즐겨찾기 취소에 성공했습니다."),
    STAR_INFO_SUCCESS(OK, "게시판 즐겨찾기 조회에 성공했습니다."),
    LIKE_SUCCESS(OK, "공감에 성공했습니다."),
    LIKE_CANCEL_SUCCESS(OK, "공감 취소에 성공했습니다."),

    HOME_INFO_SUCCESS(OK, "홈 조회에 성공했습니다."),

    SCRAP_SUCCESS(OK, "게시글 스크랩에 성공했습니다."),
    SCRAP_DELETE_SUCCESS(OK, "게시글 스크랩 취소에 성공했습니다."),

    COMMENT_SAVE_SUCCESS(OK, "댓글 작성에 성공했습니다."),
    COMMENT_DELETE_SUCCESS(OK, "댓글 삭제에 성공했습니다."),

    LECTURE_READ_SUCCESS(OK, "강의 조회에 성공했습니다."),
    LECTURE_SEARCH_SUCCESS(OK, "강의 검색에 성공했습니다."),
    TIMETABLE_ADD_SUCCESS(OK, "시간표 추가에 성공했습니다."),
    TIMETABLE_DELETE_SUCCESS(OK, "시간표 삭제에 성공했습니다."),
    TIMETABLE_READ_SUCCESS(OK, "시간표 조회에 성공했습니다."),

    MESSAGE_SEND_SUCCESS(OK, "쪽지 작성에 성공했습니다."),
    MESSAGE_REPLY_SUCCESS(OK, "쪽지 답장에 성공했습니다."),
    MESSAGE_ROOM_READ_SUCCESS(OK, "쪽지 목록 조회에 성공했습니다."),
    MESSAGE_READ_SUCCESS(OK, "쪽지 상세 조회에 성공했습니다."),

    COMMENT_LIST_READ_SUCCESS(OK, "댓글 목록 조회에 성공했습니다."),
    CHATROOM_READ_SUCCESS(OK,"채팅방 목록 조회에 성공했습니다."),
    CHATROOM_CREATE_SUCCESS(OK,"채팅방 생성에 성공했습니다."),
    CHATROOM_JOIN_SUCCESS(OK, "채팅방 입장에 성공했습니다."),
    CHATROOM_QUIT_SUCCESS(OK, "채팅방 퇴장에 성공했습니다.");

    private final HttpStatus status;
    private final String message;
}
