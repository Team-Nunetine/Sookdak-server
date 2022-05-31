package server.sookdak.dto.res.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Comment;

import java.util.List;


@Getter
@NoArgsConstructor
public class CommentListResponseDto {
    private List<CommentList> comments;

    private CommentListResponseDto(List<CommentList> comments) {
        this.comments = comments;
    }

    public static CommentListResponseDto of(List<CommentList> comments) {
        return new CommentListResponseDto(comments);
    }

    @Getter
    @NoArgsConstructor
    public static class CommentList {
        private int commentOrder;
        private Long commentId;
        private Long parent;
        private String content;
        private String imageURL;
        private int likes;
        private boolean writer;
        private boolean userLiked;
        private String createdAt;
        private List<CommentList> reply;




        public static CommentList of(Comment comment, int commentOrder, boolean writer, boolean userLiked, List<CommentList> reply) {
            CommentList commentList = new CommentList();
            commentList.commentOrder = commentOrder;
            commentList.commentId = comment.getCommentId();
            commentList.parent = comment.getParent();
            commentList.content = comment.getContent();
            if (comment.getImage() != null) {
                commentList.imageURL = comment.getImage().getUrl();
            }
            commentList.createdAt = comment.getCreatedAt();
            commentList.likes = comment.getLikes().size();
            commentList.writer = writer;
            commentList.userLiked = userLiked;
            commentList.reply = reply;


            return commentList;
        }

        public static CommentList createReply(Comment comment, int commentOrder, boolean writer, boolean userLiked) {

            CommentList commentList = new CommentList();
            commentList.commentOrder = commentOrder;
            commentList.commentId = comment.getCommentId();
            commentList.parent = comment.getParent();
            commentList.content = comment.getContent();
            if (comment.getImage() != null) {
                commentList.imageURL = comment.getImage().getUrl();
            }
            commentList.createdAt = comment.getCreatedAt();
            commentList.likes = comment.getLikes().size();
            commentList.writer = writer;
            commentList.userLiked = userLiked;

            return commentList;
        }
    }


}
