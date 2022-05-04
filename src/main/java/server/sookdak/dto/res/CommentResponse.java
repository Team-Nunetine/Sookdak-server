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

    private CommentResponse(Boolean success, String message){
        super(success, message);
    }

    public static CommentResponse of(Boolean success, String message, CommentResponseDto data) {

        return new CommentResponse(success, message, data);
    }

    public static CommentResponse ofDelete(Boolean success, String message){
        return new CommentResponse(success, message);
    }

    public static ResponseEntity<CommentResponse> newResponse(SuccessCode code, CommentResponseDto data) {
        CommentResponse response = CommentResponse.of(true, code.getMessage(), data);
        return new ResponseEntity(response, code.getStatus());
    }

    public static ResponseEntity<CommentResponse> newDeleteResponse(SuccessCode code){
        return new ResponseEntity(CommentResponse.ofDelete(true, code.getMessage()), code.getStatus());
    }
}
