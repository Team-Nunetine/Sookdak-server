package server.sookdak.dto.res.chat;

import lombok.Getter;
import server.sookdak.domain.ChatRoom;

@Getter
public class ChatRoomResponseDto {
    private Long roomId;
    private ChatRoomResponseDto(ChatRoom entity) { this.roomId = entity.getRoomId(); }
    public static ChatRoomResponseDto of(ChatRoom entity) { return new ChatRoomResponseDto(entity); }
}
