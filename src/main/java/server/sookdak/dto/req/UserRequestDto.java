package server.sookdak.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import server.sookdak.domain.Authority;
import server.sookdak.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {
    private String email;

    public User toUser(PasswordEncoder passwordEncoder, String key) {
        return User.createUser(email, passwordEncoder.encode(key), Authority.ROLE_USER);
    }

    public UsernamePasswordAuthenticationToken toAuthentication(String key) {
        return new UsernamePasswordAuthenticationToken(email, key);
    }
}
