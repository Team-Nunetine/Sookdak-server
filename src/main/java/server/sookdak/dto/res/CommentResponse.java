package server.sookdak.dto.res;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
public class CommentResponse extends BaseResponse {
    CommentResponseDto data;

    private CommentResponse(Boolean success, String message, CommentResponseDto data){
        super(success, message);
        this.data = data;
    }

    public static CommentResponse of(Boolean success, String message, CommentResponseDto data) {

        return new CommentResponse(success, message, data);
    }

    public static ResponseEntity<CommentResponse> newResponse(SuccessCode code, CommentResponseDto data) {
        CommentResponse response = CommentResponse.of(true, code.getMessage(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
