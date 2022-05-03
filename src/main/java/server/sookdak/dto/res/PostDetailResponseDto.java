package server.sookdak.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostDetailResponseDto {
    private PostDetail post;

    private PostDetailResponseDto(PostDetail post) {
        this.post = post;
    }

    public static PostDetailResponseDto of(PostDetail post) {
        return new PostDetailResponseDto(post);
    }

    @Getter
    @NoArgsConstructor
    public static class PostDetail {
        private String content;
        private String createdAt;
        private int likes;
        private int scraps;
        private int comments;
        private List<String> images;
        private boolean writer;

        public PostDetail(String content, String createdAt, int likes, int scraps, int comments, List<String> images, boolean writer) {
            this.content = content;
            this.createdAt = createdAt;
            this.likes = likes;
            this.scraps = scraps;
            this.comments = comments;
            this.images = images;
            this.writer = writer;
        }
    }
}
