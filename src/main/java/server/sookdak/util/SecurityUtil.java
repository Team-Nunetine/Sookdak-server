package server.sookdak.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import server.sookdak.constants.ExceptionCode;
import server.sookdak.exception.CustomException;

public class SecurityUtil {

    private SecurityUtil() {
    }

    //SecurityContext의 Authentication 객체를 이용해 email 리턴
    public static String getCurrentUserEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }
        if (authentication.getName().equals("anonymousUser")) {
            throw new CustomException(ExceptionCode.ACCESS_DENIED);
        }

        return authentication.getName();
    }
}
