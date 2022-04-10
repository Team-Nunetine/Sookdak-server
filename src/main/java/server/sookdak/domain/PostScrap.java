package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostScrap {

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

    private LocalDateTime createdAt;

    public static PostScrap createPostScrap(User user, Post post){
        PostScrap postScrap = new PostScrap();
        postScrap.userPostId = new UserPostId(user.getUserId(), post.getPostId());
        postScrap.user = user;
        postScrap.post = post;
        postScrap.createdAt = LocalDateTime.now();
        return postScrap;
    }

}
