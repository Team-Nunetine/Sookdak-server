package server.sookdak.dto.res.chat;

import lombok.Getter;
import server.sookdak.domain.Chat;
import server.sookdak.domain.User;

@Getter
public class ChatResponseDto {
    private Long chatId;
    private Long roomId;
    private Long userId;
    private String content;
    private String createdAt;

    private ChatResponseDto(Chat entity) {
        this.chatId = entity.getChatId();
        this.userId = entity.getUser().getUserId();
        this.roomId = entity.getChatRoom().getRoomId();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
    }
    public static ChatResponseDto of(Chat entity) { return new ChatResponseDto(entity); }

}
