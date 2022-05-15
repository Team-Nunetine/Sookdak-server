package server.sookdak.dto.res.comment;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
public class CommentDetailResponse extends BaseResponse {
    CommentDetailResponseDto data;

    private CommentDetailResponse(Boolean success, String message, CommentDetailResponseDto data){
        super(success, message);
        this.data = data;
    }


    public static CommentDetailResponse of(Boolean success, String message, CommentDetailResponseDto data) {

        return new CommentDetailResponse(success, message, data);
    }


    public static ResponseEntity<CommentDetailResponse> newResponse(SuccessCode code, CommentDetailResponseDto data) {
        CommentDetailResponse response = CommentDetailResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }

}
