package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarId implements Serializable {
    private Long userId;
    private Long boardId;

    public StarId(Long userId, Long boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }
}
