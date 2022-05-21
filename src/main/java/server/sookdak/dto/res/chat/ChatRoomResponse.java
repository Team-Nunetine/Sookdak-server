package server.sookdak.dto.res.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class ChatRoomResponse extends BaseResponse {
    ChatRoomResponseDto data;

    private ChatRoomResponse(Boolean success, String msg, ChatRoomResponseDto data) {
        super(success,msg);
        this.data = data;
    }

    public static ChatRoomResponse of(Boolean success, String msg, ChatRoomResponseDto data) {
        return new ChatRoomResponse(success, msg, data);
    }

    public static ResponseEntity<ChatRoomResponse> newResponse(SuccessCode code, ChatRoomResponseDto data) {
        ChatRoomResponse response =  ChatRoomResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
