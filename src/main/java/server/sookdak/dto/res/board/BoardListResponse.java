package server.sookdak.dto.res.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class BoardListResponse extends BaseResponse {
    BoardListResponseDto data;

    private BoardListResponse(Boolean success, String msg, BoardListResponseDto data) {
        super(success, msg);
        this.data = data;
    }

    public static BoardListResponse of(Boolean success, String msg, BoardListResponseDto data) {
        return new BoardListResponse(success, msg, data);
    }

    public static ResponseEntity<BoardListResponse> newResponse(SuccessCode code, BoardListResponseDto data) {
        BoardListResponse response = BoardListResponse.of(true, code.getMessage(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
