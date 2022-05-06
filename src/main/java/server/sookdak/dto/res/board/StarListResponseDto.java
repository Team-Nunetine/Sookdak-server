package server.sookdak.dto.res.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Star;

import java.util.List;

@Getter
@NoArgsConstructor
public class StarListResponseDto {
    private List<StarList> stars;

    private StarListResponseDto(List<StarList> stars) {
        this.stars = stars;
    }

    public static StarListResponseDto of(List<StarList> stars) {
        return new StarListResponseDto(stars);
    }

    @Getter
    public static class StarList {
        private Long boardId;
        private String name;
        private String description;

        public StarList(Star star) {
            this.boardId = star.getBoard().getBoardId();
            this.name = star.getBoard().getName();
            this.description = star.getBoard().getDescription();
        }
    }
}
