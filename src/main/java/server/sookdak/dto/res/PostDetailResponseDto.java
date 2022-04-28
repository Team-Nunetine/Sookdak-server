package server.sookdak.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostDetailResponseDto {
    private PostDetail post;
    private List<CommentList> comments;

    private PostDetailResponseDto(PostDetail post, List<CommentList> comments) {
        this.post = post;
        this.comments = comments;
    }

    public static PostDetailResponseDto of(PostDetail post, List<CommentList> comments) {
        return new PostDetailResponseDto(post, comments);
    }

    @Getter
    @NoArgsConstructor
    public static class PostDetail {
        private String content;
        private String createdAt;
        private int likes;
        private int scraps;
        private List<String> images;
        private boolean writer;

        public PostDetail(String content, String createdAt, int likes, int scraps, List<String> images, boolean writer) {
            this.content = content;
            this.createdAt = createdAt;
            this.likes = likes;
            this.scraps = scraps;
            this.images = images;
            this.writer = writer;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CommentList {
        private Long commentId;
        private String content;
        private String createdAt;
        private int likes;
        private String image;

        public CommentList(Long commentId, String content, String createdAt, int likes, String image) {
            this.commentId = commentId;
            this.content = content;
            this.createdAt = createdAt;
            this.likes = likes;
            this.image = image;
        }
    }
}
