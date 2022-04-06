package server.sookdak.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostSaveRequestDto {

    @NotBlank(message = "게시글 내용이 없습니다.")
    @Length(max = 2000, message = "글자수가 2000자를 초과했습니다.")
    private String content;

    @Size(max = 10, message = "이미지 개수가 10개를 초과했습니다.")
    private List<MultipartFile> images = new ArrayList<>();

}
