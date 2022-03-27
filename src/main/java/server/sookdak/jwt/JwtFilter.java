package server.sookdak.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import server.sookdak.exception.CustomException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static server.sookdak.constants.ExceptionCode.*;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    //토큰의 인증정보를 현재 실행 중인 SecurityContext에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 1. Request Header 에서 토큰을 꺼냄
        String jwt = resolveToken(request);

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        try{
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", WRONG_TYPE_TOKEN);
            log.info("잘못된 JWT 서명입니다.");
            throw new CustomException(WRONG_TYPE_TOKEN);
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", EXPIRED_TOKEN);
            log.info("만료된 JWT 토큰입니다.");
            throw new CustomException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", UNSUPPORTED_TOKEN);
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new CustomException(UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", WRONG_TOKEN);
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new CustomException(WRONG_TOKEN);
        } catch(Exception e) {
            log.error("===========================================");
            log.error("JwtFilter - doFilterInternal() 오류 발생");
            log.error("Exception message : {}", e.getMessage());
            log.error("===========================================");
            request.setAttribute("exception", UNKNOWN_ERROR);
            throw new CustomException(UNKNOWN_ERROR);
        }

        filterChain.doFilter(request, response);
    }

    //Request Header에서 토큰 정보 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

