package server.sookdak.dto.res;

import lombok.Getter;
import server.sookdak.domain.Board;

@Getter
public class BoardListResponseDto {
    private String name;
    private String description;

    public BoardListResponseDto(Board entity){
        this.name = entity.getName();
        this.description = entity.getDescription();
    }
}
