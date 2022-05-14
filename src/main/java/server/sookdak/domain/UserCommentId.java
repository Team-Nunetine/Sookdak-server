package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCommentId implements Serializable {
    private Long userId;
    private Long commentId;

    public UserCommentId(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }
}
