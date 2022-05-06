package server.sookdak.dto.res.post;

import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

public class PostResponse extends BaseResponse {

    private PostResponse(Boolean success, String message) {
        super(success, message);
    }

    public static PostResponse of(Boolean success, String message) {
        return new PostResponse(success, message);
    }

    public static ResponseEntity<PostResponse> newResponse(SuccessCode code) {
        return new ResponseEntity(PostResponse.of(true, code.getMessage()), code.getStatus());
    }
}
