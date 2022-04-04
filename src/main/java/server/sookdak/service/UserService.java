package server.sookdak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.constants.ExceptionCode;
import server.sookdak.domain.User;
import server.sookdak.dto.TokenDto;
import server.sookdak.dto.req.TokenRequestDto;
import server.sookdak.dto.req.UserRequestDto;
import server.sookdak.dto.res.UserResponseDto;
import server.sookdak.exception.CustomException;
import server.sookdak.jwt.TokenProvider;
import server.sookdak.repository.UserRepository;
import server.sookdak.util.SecurityUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.key}")
    private String key;


    //현재 SecurityContext에 있는 유저 정보 가져오기
    public UserResponseDto getMyInfo() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));
        return UserResponseDto.toUserResponseDto(userEmail, user.getAuthority());
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.USER_NOT_FOUND));
    }


    public TokenDto login(UserRequestDto userRequestDto) {
        if (!(userRequestDto.getEmail().contains("sookmyung.ac.kr") || userRequestDto.getEmail().contains("sm.ac.kr"))) {
            throw new CustomException(ExceptionCode.SOOKMYUNG_ONLY);
        }
        if (!userRepository.existsByEmail(userRequestDto.getEmail())) {
            // signup
            User user = userRequestDto.toUser(passwordEncoder, key);
            userRepository.save(user);
            return null;
        }

        //1. AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication(key);

        //2. 검증 이뤄짐
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //3. 인증 정보 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        //4. refreshToken 저장
        User user = userRepository.findByEmail(userRequestDto.getEmail()).get();
        user.setRefreshToken(tokenDto.getRefreshToken());

        //5. 토큰 발급
        return tokenDto;
    }

    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        //1. refreshToken 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        //2. AccessToken에서 UserId 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        //3. 저장소에서 UserId 기반으로 RefreshToken 값 가져오기
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
        String refreshToken = user.getRefreshToken();

        //4. Refresh Token 일치하는지 검사
        if (!refreshToken.equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        //5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        //6. 저장소 정보 업데이트
        user.setRefreshToken(tokenDto.getRefreshToken());

        //7. 토큰 발급
        return tokenDto;
    }
}
