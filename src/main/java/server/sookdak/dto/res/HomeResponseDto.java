package server.sookdak.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Board;
import server.sookdak.domain.Post;
import server.sookdak.domain.Star;
import server.sookdak.dto.res.PostListResponseDto.PostList;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class HomeResponseDto {
    private List<StarBoardList> stars;

    private HomeResponseDto(List<StarBoardList> stars) {
        this.stars = stars;
    }

    public static HomeResponseDto of(List<StarBoardList> starBoards) {
        return new HomeResponseDto(starBoards);
    }

    @Getter
    public static class StarBoardList {
        private Long boardId;
        private String boardName;
        private List<PostList> postList;

        public StarBoardList(Star star, List<Post> post) {
            this.boardId = star.getBoard().getBoardId();
            this.boardName = star.getBoard().getName();
            List<PostList> posts = new ArrayList<>();
            for (Post p : post) {
                posts.add(PostList.of(p));
            }
            this.postList = posts;
        }
    }
}
