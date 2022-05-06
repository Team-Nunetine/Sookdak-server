package server.sookdak.dto.res.board;

import lombok.Getter;
import server.sookdak.domain.Board;

@Getter
public class BoardResponseDto {
    private Long boardId;

    private BoardResponseDto(Board entity){
        this.boardId = entity.getBoardId();
    }

    public static BoardResponseDto of(Board entity){
        return new BoardResponseDto(entity);
    }

}
