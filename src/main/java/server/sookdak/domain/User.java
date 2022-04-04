package server.sookdak.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user")
    private List<Board> board = new ArrayList<>();


    public static User createUser(String email, String password, Authority authority) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.authority = authority;
        return user;
    }

    public void setRefreshToken(String refreshToken) {this.refreshToken = refreshToken;}

    public void createBoard(Board board){
        this.board.add(board);
        board.createdByUser(this);
    }
}
