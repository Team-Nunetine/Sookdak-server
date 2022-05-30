package server.sookdak.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ChatRequsetDto {
    @NotBlank(message = "채팅 내용이 없습니다.")
    @Length(max = 1000, message = "글자수가 1000자를 초과했습니다.")
    private String content;


}
