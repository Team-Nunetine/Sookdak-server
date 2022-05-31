package server.sookdak.dto.res.message;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
public class MessageRoomResponse extends BaseResponse {
    MessageRoomResponseDto data;

    private MessageRoomResponse(Boolean success, String message, MessageRoomResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public static MessageRoomResponse of(Boolean success, String message, MessageRoomResponseDto data) {
        return new MessageRoomResponse(success, message, data);
    }

    public static ResponseEntity<MessageRoomResponse> newResponse(SuccessCode code, MessageRoomResponseDto data) {
        MessageRoomResponse response = MessageRoomResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
