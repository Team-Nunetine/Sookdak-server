package server.sookdak.dto.res.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Post;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    private boolean star;
    private List<PostList> posts;

    private PostListResponseDto(boolean star, List<PostList> posts) {
        this.star = star;
        this.posts = posts;
    }

    public static PostListResponseDto of(boolean star, List<PostList> posts) {
        return new PostListResponseDto(star, posts);
    }

    @Getter
    @NoArgsConstructor
    public static class PostList {
        private Long postId;
        private String content;
        private String createdAt;
        private int likes;
        private int comments;
        private boolean image;

        public static PostList of(Post post) {
            PostList postList = new PostList();
            postList.postId = post.getPostId();
            postList.content = post.getContent();
            postList.createdAt = post.getCreatedAt();
            postList.likes = post.getLikes().size();
            postList.comments = post.getComments().size();
            if (post.getImages().size() > 0) {
                postList.image = true;
            } else {
                postList.image = false;
            }
            return postList;
        }
    }
}
