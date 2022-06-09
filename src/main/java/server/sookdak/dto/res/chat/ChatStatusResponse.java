package server.sookdak.dto.res.chat;

import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

public class ChatStatusResponse extends BaseResponse {
    private ChatStatusResponse(Boolean success, String message) {super(success, message); }

    public static ChatStatusResponse of(Boolean success, String message) { return new ChatStatusResponse(success, message); }

    public static ResponseEntity<ChatStatusResponse> newResponse(SuccessCode code) {
        return new ResponseEntity<>(ChatStatusResponse.of(true, code.getMessage()), code.getStatus());
    }

}
