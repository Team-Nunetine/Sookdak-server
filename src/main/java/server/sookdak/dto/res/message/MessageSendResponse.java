package server.sookdak.dto.res.message;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
public class MessageSendResponse extends BaseResponse {
    MessageSendResponseDto data;

    private MessageSendResponse(Boolean success, String message, MessageSendResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public static MessageSendResponse of(Boolean success, String message, MessageSendResponseDto data) {
        return new MessageSendResponse(success, message, data);
    }

    public static ResponseEntity<MessageSendResponse> newResponse(SuccessCode code, MessageSendResponseDto data) {
        MessageSendResponse response = MessageSendResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
