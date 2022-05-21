package server.sookdak.dto.res.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class ChatRoomListResponse extends BaseResponse {

    ChatRoomListResponseDto data;

    private ChatRoomListResponse(Boolean success, String msg, ChatRoomListResponseDto data) {
        super(success, msg);
        this.data = data;
    }

    public static ChatRoomListResponse of(Boolean success, String msg, ChatRoomListResponseDto data) {
        return new ChatRoomListResponse(success, msg, data);
    }

    public static ResponseEntity<ChatRoomListResponse> newResponse(SuccessCode code, ChatRoomListResponseDto data) {
        ChatRoomListResponse response = ChatRoomListResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
