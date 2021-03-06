package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sookdak.dto.res.post.*;
import server.sookdak.dto.res.user.TokenDto;
import server.sookdak.dto.req.TokenRequestDto;
import server.sookdak.dto.req.UserRequestDto;
import server.sookdak.dto.res.board.BoardListResponse;
import server.sookdak.dto.res.board.BoardListResponseDto;
import server.sookdak.dto.res.user.TokenResponse;
import server.sookdak.dto.res.user.UserResponse;
import server.sookdak.dto.res.user.UserResponseDto;
import server.sookdak.service.BoardService;
import server.sookdak.service.PostService;
import server.sookdak.service.UserService;

import javax.validation.Valid;

import static server.sookdak.constants.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApi {

    private final UserService userService;
    private final BoardService boardService;
    private final PostService postService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyMemberInfo() {
        UserResponseDto userResponseDto = userService.getMyInfo();
        return UserResponse.toResponse(USER_INFO_SUCCESS, userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody UserRequestDto userRequestDto) {
        TokenDto tokenDto = userService.login(userRequestDto);

        return TokenResponse.toResponse(LOGIN_SUCCESS, tokenDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto tokenDto = userService.reissue(tokenRequestDto);
        return TokenResponse.toResponse(REISSUE_SUCCESS, tokenDto);
    }

    @GetMapping("/my-board")
    public ResponseEntity<BoardListResponse> findAll() {
        BoardListResponseDto responseDto = boardService.findByUser();

        return BoardListResponse.newResponse(BOARD_READ_SUCCESS, responseDto);
    }

    @GetMapping("/my-post/{page}")
    public ResponseEntity<MyPostListResponse> mypost(@PathVariable int page) {
        MyPostListResponseDto responseDto = postService.getMyPost(page);

        return MyPostListResponse.newResponse(POST_LIST_READ_SUCCESS, responseDto);
    }

    @GetMapping("/my-scrap/{page}")
    public ResponseEntity<MyPostListResponse> myscrap(@PathVariable int page) {
        MyPostListResponseDto responseDto = postService.getMyScrap(page);

        return MyPostListResponse.newResponse(POST_LIST_READ_SUCCESS,responseDto);
    }

    @GetMapping("/my-comment/{page}")
    public ResponseEntity<MyPostListResponse> mycomment(@PathVariable int page) {
        MyPostListResponseDto responseDto = postService.getMyComment(page);

        return MyPostListResponse.newResponse(POST_LIST_READ_SUCCESS, responseDto);
    }
}
