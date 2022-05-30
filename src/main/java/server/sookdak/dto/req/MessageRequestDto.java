package server.sookdak.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class MessageRequestDto {

    @NotBlank(message = "쪽지 내용이 없습니다.")
    @Length(max = 500, message = "글자수가 500자를 초과했습니다.")
    private String content;
}
