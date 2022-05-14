package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {

    @EmbeddedId
    private UserCommentId userCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @MapsId("commentId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment comment;

    public static CommentLike createCommentLike(User user, Comment comment) {
        CommentLike commentLike = new CommentLike();
        commentLike.userCommentId = new UserCommentId(user.getUserId(), comment.getCommentId());
        commentLike.user = user;
        commentLike.comment = comment;
        return commentLike;
    }
}
