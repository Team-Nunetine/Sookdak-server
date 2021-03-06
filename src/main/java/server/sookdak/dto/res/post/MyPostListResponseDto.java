package server.sookdak.dto.res.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.dto.res.post.PostListResponseDto.PostList;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyPostListResponseDto {
    private List<PostList> posts;

    private MyPostListResponseDto(List<PostList> posts) {
        this.posts = posts;
    }

    public static MyPostListResponseDto of(List<PostList> posts) {
        return new MyPostListResponseDto(posts);
    }
}
