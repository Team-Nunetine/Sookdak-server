package server.sookdak.dto.res.message;

import lombok.Getter;

@Getter
public class MessageSendResponseDto {
    Long roomId;

    private MessageSendResponseDto(Long roomId) {
        this.roomId = roomId;
    }

    public static MessageSendResponseDto of(Long roomId) {
        return new MessageSendResponseDto(roomId);
    }
}
