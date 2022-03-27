package server.sookdak.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(length = 100)
    private String email;

    private String password;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public static User createUser(String email, String password, Authority authority) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.authority = authority;
        return user;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
