package server.sookdak.dto.res.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Message;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MessageDetailResponseDto {
    List<MessageDto> messages;

    private MessageDetailResponseDto(List<MessageDto> messages) {
        this.messages = messages;
    }

    public static MessageDetailResponseDto of(List<MessageDto> messages) {
        return new MessageDetailResponseDto(messages);
    }

    @Getter
    @NoArgsConstructor
    public static class MessageDto {
        private Long messageId;
        private String content;
        @JsonFormat(pattern = "MM/dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime dateTime;
        private boolean sender;

        public static MessageDto of(Message message, boolean sender) {
            MessageDto messageDto = new MessageDto();
            messageDto.messageId = message.getMessageId();
            messageDto.content = message.getContent();
            messageDto.dateTime = message.getCreatedAt();
            messageDto.sender = sender;
            return messageDto;
        }
    }
}
