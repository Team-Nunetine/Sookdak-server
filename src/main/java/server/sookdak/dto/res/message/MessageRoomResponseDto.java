package server.sookdak.dto.res.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Message;
import server.sookdak.domain.MessageRoom;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MessageRoomResponseDto {
    private List<RoomList> rooms;

    private MessageRoomResponseDto(List<RoomList> rooms) {
        this.rooms = rooms;
    }

    public static MessageRoomResponseDto of(List<RoomList> rooms) {
        return new MessageRoomResponseDto(rooms);
    }

    @Getter
    @NoArgsConstructor
    public static class RoomList implements Comparable<RoomList> {
        private Long roomId;
        private String recentContent;
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime recentDateTime;

        public static RoomList of(MessageRoom messageRoom, Message message) {
            RoomList roomList = new RoomList();
            roomList.roomId = messageRoom.getRoomId();
            roomList.recentContent = message.getContent();
            roomList.recentDateTime = message.getCreatedAt();
            return roomList;
        }

        @Override
        public int compareTo(RoomList o) {
            if (recentDateTime.isBefore(o.recentDateTime)) {
                return 1;
            }
            return -1;
        }
    }
}
