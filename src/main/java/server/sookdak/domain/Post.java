package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(length = 2000)
    private String content;

    private String createdAt;

    private Long liked;

    public static Post createPost(User user, Board board, String content, String createdAt) {
        Post post = new Post();
        post.user = user;
        post.board = board;
        post.content = content;
        post.createdAt = createdAt;
        post.liked = 0L;
        return post;
    }
}
