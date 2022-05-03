package server.sookdak.dto.res;

import lombok.Getter;
import server.sookdak.domain.Comment;

@Getter
public class CommentResponseDto {
    private int commentOrder;
    private Long comment_id;
    private Long parent;
    private String content;
    private String imageURL;
    private String createdAt;


    private CommentResponseDto(int commentOrder, Comment entity, String imageURL) {
        this.commentOrder = commentOrder;
        this.comment_id = entity.getCommentId();
        this.parent = entity.getParent();
        this.content = entity.getContent();
        this.imageURL = imageURL;
        this.createdAt = entity.getCreatedAt();

    }

    public static CommentResponseDto of(int commentOrder, Comment entity, String imageURL) {
        return new CommentResponseDto(commentOrder, entity,imageURL);
    }
}


