package server.sookdak.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class PostListResponse extends BaseResponse {
    PostListResponseDto data;

    private PostListResponse(Boolean success, String message, PostListResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public static PostListResponse of(Boolean success, String message, PostListResponseDto data) {
        return new PostListResponse(success, message, data);
    }

    public static ResponseEntity<PostListResponse> newResponse(SuccessCode code, PostListResponseDto data) {
        PostListResponse response = PostListResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
