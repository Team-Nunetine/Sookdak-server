package server.sookdak.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Post;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    private List<PostList> posts;

    private PostListResponseDto(List<PostList> posts) {
        this.posts = posts;
    }

    public static PostListResponseDto of(List<PostList> posts) {
        return new PostListResponseDto(posts);
    }

    @Getter
    public static class PostList {
        private Long postId;
        private String content;
        private String createdAt;
        private int likes;
        private boolean image;

        public PostList(Post entity, boolean image, int likes) {
            this.postId = entity.getPostId();
            this.content = entity.getContent();
            this.createdAt = entity.getCreatedAt();
            this.likes = likes;
            this.image = image;
        }
    }
}
