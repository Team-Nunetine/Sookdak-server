package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @Column(length = 2000)
    private String content;

    private LocalDateTime createdAt;

    private Long parent;

    @OneToOne(mappedBy = "comment", cascade = CascadeType.ALL)
    private CommentImage image;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> likes = new ArrayList<>();

    public static Comment createComment(User user, Post post, Long parent, String content, LocalDateTime createdAt){
        Comment comment = new Comment();
        comment.user = user;
        comment.post = post;
        comment.parent = parent;
        comment.content = content;
        comment.createdAt = createdAt;
        return comment;
    }
}
