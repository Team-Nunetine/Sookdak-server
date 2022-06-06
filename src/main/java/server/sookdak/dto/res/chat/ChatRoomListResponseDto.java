package server.sookdak.dto.res.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.ChatRoom;
import server.sookdak.domain.User;

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
        private int users;
        private boolean status;


        public static ChatRoomList of(ChatRoom chatRoom, boolean status) {
            ChatRoomList chatRoomList = new ChatRoomList();
            chatRoomList.roomId = chatRoom.getRoomId();
            chatRoomList.name = chatRoom.getName();
            chatRoomList.info = chatRoom.getInfo();
            chatRoomList.users= chatRoom.getUsers().size();
            chatRoomList.status = status;

            return chatRoomList;
        }
    }
}
