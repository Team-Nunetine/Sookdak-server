package server.sookdak.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomSaveRequestDto {
    @NotBlank(message = "채팅방 이름이 없습니다.")
    private String name;

    private String info;
}
