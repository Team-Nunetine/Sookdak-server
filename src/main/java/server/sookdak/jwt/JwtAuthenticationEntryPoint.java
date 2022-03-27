package server.sookdak.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import server.sookdak.constants.ExceptionCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static server.sookdak.constants.ExceptionCode.*;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String exception = String.valueOf(request.getAttribute("exception"));

        if (exception == null) {
            log.info("알 수 없는 인증 에러입니다.");
            setResponse(response, UNKNOWN_ERROR);
        } else if (exception.equals("WRONG_TYPE_TOKEN")) {
            setResponse(response, WRONG_TYPE_TOKEN);
        } else if (exception.equals("EXPIRED_TOKEN")) {
            setResponse(response, EXPIRED_TOKEN);
        } else if (exception.equals("UNSUPPORTED_TOKEN")) {
            setResponse(response, UNSUPPORTED_TOKEN);
        } else {
            setResponse(response, ACCESS_DENIED);
        }
        return;
    }

    private void setResponse(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.getWriter().println("{ \"success\" : false, \"message\" : \"" + exceptionCode.getMessage()
                + "\", \"timestamp\" : \"" + LocalDateTime.now() + "\" \n }");
    }
}
