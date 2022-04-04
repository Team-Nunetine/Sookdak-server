package server.sookdak.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardSaveRequestDto {
    @NotBlank(message = "게시판 이름이 없습니다.")
    private String name;

    private String description;
}
