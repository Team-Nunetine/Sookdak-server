package server.sookdak.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardSaveRequestDto {
    private String name;
    private String description;
}
