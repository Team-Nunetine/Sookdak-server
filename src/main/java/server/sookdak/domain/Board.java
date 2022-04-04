package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    @NotNull(message = "이름을 입력해주세요")
    @Column(length = 20)
    private String name;

    private String description;

    public static Board createBoard(User user, String name, String description) {
        Board board = new Board();
        board.user = user;
        board.name = name;
        board.description = description;
        return board;
    }
}
