package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Star {

    @EmbeddedId
    private StarId starId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @MapsId("boardId")
    private Board board;

    private LocalDateTime createdAt;

    public static Star createStar(User user, Board board) {
        Star star = new Star();
        star.starId = new StarId(user.getUserId(), board.getBoardId());
        star.user = user;
        star.board = board;
        star.createdAt = LocalDateTime.now();
        return star;
    }
}
