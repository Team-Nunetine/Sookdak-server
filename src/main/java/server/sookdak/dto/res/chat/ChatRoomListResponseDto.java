package server.sookdak.dto.res.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.ChatRoom;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomListResponseDto {
    private List<ChatRoomList> chatRooms;
    private ChatRoomListResponseDto(List<ChatRoomList> chatRooms){ this.chatRooms = chatRooms; }
    public static ChatRoomListResponseDto of(List<ChatRoomList> chatRooms) { return new ChatRoomListResponseDto(chatRooms); }

    @Getter
    public static class ChatRoomList {
        private Long roomId;
        private String name;
        private String info;

        public ChatRoomList(ChatRoom entity) {
            this.roomId = entity.getRoomId();
            this.name = entity.getName();
            this.info = entity.getInfo();
        }
    }
}
