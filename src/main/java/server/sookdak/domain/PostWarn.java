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
public class PostWarn {

    @EmbeddedId
    private UserPostId userPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @MapsId("postId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    public static PostWarn createPostWarn(User user, Post post) {
        PostWarn postWarn = new PostWarn();
        postWarn.userPostId = new UserPostId(user.getUserId(), post.getPostId());
        postWarn.user = user;
        postWarn.post = post;
        return postWarn;
    }
}
