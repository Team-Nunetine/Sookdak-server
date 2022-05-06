package server.sookdak.dto.res.board;

import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

public class StarResponse extends BaseResponse {

    private StarResponse(Boolean success, String message) {
        super(success, message);
    }

    public static StarResponse of(Boolean success, String message) {
        return new StarResponse(success, message);
    }

    public static ResponseEntity<StarResponse> newResponse(SuccessCode code) {
        return new ResponseEntity<>(StarResponse.of(true, code.getMessage()), code.getStatus());
    }
}
