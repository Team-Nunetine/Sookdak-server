package server.sookdak.dto.res.message;

import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

public class MessageResponse extends BaseResponse {

    private MessageResponse(Boolean success, String message) {
        super(success, message);
    }

    public static MessageResponse of(Boolean success, String message) {
        return new MessageResponse(success, message);
    }

    public static ResponseEntity<MessageResponse> newResponse(SuccessCode code) {
        return new ResponseEntity<>(MessageResponse.of(true, code.getMessage()), code.getStatus());
    }
}
