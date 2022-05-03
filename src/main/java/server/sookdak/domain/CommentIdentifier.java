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
public class CommentIdentifier {

    @EmbeddedId
    private UserPostId userPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    @MapsId("postId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    private int commentOrder;

    public static CommentIdentifier createCommentIdentifier(User user, Post post, int commentOrder){
        CommentIdentifier commentIdentifier = new CommentIdentifier();
        commentIdentifier.userPostId = new UserPostId(user.getUserId(), post.getPostId());
        commentIdentifier.user = user;
        commentIdentifier.post = post;
        commentIdentifier.commentOrder = commentOrder;
        return commentIdentifier;
    }
}
