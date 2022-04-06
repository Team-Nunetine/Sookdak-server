package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sookdak.dto.TokenDto;
import server.sookdak.dto.req.TokenRequestDto;
import server.sookdak.dto.res.TokenResponse;
import server.sookdak.dto.res.UserResponse;
import server.sookdak.dto.res.UserResponseDto;
import server.sookdak.dto.req.UserRequestDto;
import server.sookdak.service.UserService;

import javax.validation.Valid;

import static server.sookdak.constants.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApi {

    private final UserService userService;

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
}
