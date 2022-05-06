package server.sookdak.dto.res.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class BoardResponse extends BaseResponse {
    BoardResponseDto data;

    private BoardResponse(Boolean success, String msg, BoardResponseDto data) {
        super(success, msg);
        this.data = data;
    }

    public static BoardResponse of(Boolean success, String msg, BoardResponseDto data) {

        return new BoardResponse(success, msg, data);
    }

    public static ResponseEntity<BoardResponse> newResponse(SuccessCode code, BoardResponseDto data) {
        BoardResponse response = BoardResponse.of(true, code.getMessage(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
