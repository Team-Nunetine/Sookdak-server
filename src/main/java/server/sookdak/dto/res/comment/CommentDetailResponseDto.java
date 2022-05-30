package server.sookdak.dto.res.comment;

import lombok.Getter;
import server.sookdak.domain.Comment;

@Getter
public class CommentDetailResponseDto {
    private int commentOrder;
    private Long commentId;
    private Long parent;
    private String content;
    private String imageURL;
    private int likes;
    private String createdAt;



    private CommentDetailResponseDto(int commentOrder, Comment entity, String imageURL) {
        this.commentOrder = commentOrder;
        this.commentId = entity.getCommentId();
        this.parent = entity.getParent();
        this.content = entity.getContent();
        this.imageURL = imageURL;
        this.likes = entity.getLikes().size();
        this.createdAt = entity.getCreatedAt();


    }

    public static CommentDetailResponseDto of(int commentOrder, Comment entity, String imageURL) {
        return new CommentDetailResponseDto(commentOrder, entity,imageURL);
    }
}


