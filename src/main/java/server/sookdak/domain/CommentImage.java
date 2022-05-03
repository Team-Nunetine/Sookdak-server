package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentImage {
    @Id
    @Column(name = "comment_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentImageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment comment;

    private String url;

    public static CommentImage createCommentImage(Comment comment, String url){
        CommentImage commentImage = new CommentImage();
        commentImage.comment = comment;
        commentImage.url = url;
        return commentImage;
    }
}