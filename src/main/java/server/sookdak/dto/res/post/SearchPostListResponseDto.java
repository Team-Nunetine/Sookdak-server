package server.sookdak.dto.res.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class SearchPostListResponseDto {
    private List<SearchPostList> posts;

    private SearchPostListResponseDto(List<SearchPostList> posts) {
        this.posts = posts;
    }

    public static SearchPostListResponseDto of(List<SearchPostList> posts) {
        return new SearchPostListResponseDto(posts);
    }

    @Getter
    @NoArgsConstructor
    public static class SearchPostList {
        private Long postId;
        private String content;
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        private String boardName;
        private int likes;
        private int comments;
        private boolean image;

        public static SearchPostList of(Post post) {
            SearchPostList postList = new SearchPostList();
            postList.postId = post.getPostId();
            postList.content = post.getContent();
            postList.createdAt = post.getCreatedAt();
            postList.boardName = post.getBoard().getName();
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
