package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPostId implements Serializable {
    private Long userId;
    private Long postId;

    public UserPostId(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
