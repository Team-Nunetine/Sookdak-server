package server.sookdak.dto.res.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentDetailResponseDto {
    private int commentOrder;
    private Long commentId;
    private Long parent;
    private String content;
    private String imageURL;
    private int likes;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

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
        return new CommentDetailResponseDto(commentOrder, entity, imageURL);
    }
}


