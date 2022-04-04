package server.sookdak.dto.res;

import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

public class BoardResponse extends BaseResponse {

    private BoardResponse(Boolean success, String msg) {
        super(success, msg);
    }

    public static BoardResponse of(Boolean success, String msg) {
        return new BoardResponse(success, msg);
    }

    public static ResponseEntity<BoardResponse> newResponse(SuccessCode code) {
        return new ResponseEntity(BoardResponse.of(true, code.getMessage()), code.getStatus());
    }
}
