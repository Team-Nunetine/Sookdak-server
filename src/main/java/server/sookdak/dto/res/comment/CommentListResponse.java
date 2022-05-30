package server.sookdak.dto.res.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class CommentListResponse extends BaseResponse {
    CommentListResponseDto data;

    private CommentListResponse(Boolean success, String msg, CommentListResponseDto data) {
        super(success, msg);
        this.data = data;
    }

    public static CommentListResponse of(Boolean success, String msg, CommentListResponseDto data) {
        return new CommentListResponse(success, msg, data);
    }

    public static ResponseEntity<CommentListResponse> newResponse(SuccessCode code, CommentListResponseDto data) {
        CommentListResponse response = CommentListResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }

}
