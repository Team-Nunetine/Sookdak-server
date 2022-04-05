package server.sookdak.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Board;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardListResponseDto {
    private List<BoardList> boards;

    private BoardListResponseDto(List<BoardList> boards) {
        this.boards = boards;
    }

    public static BoardListResponseDto of(List<BoardList> boards) {
        return new BoardListResponseDto(boards);
    }

    @Getter
    public static class BoardList {
        private Long boardId;
        private String name;
        private String description;

        public BoardList(Board entity){
            this.boardId = entity.getBoardId();
            this.name = entity.getName();
            this.description = entity.getDescription();
        }
    }
}
