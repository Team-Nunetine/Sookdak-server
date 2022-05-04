package server.sookdak.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class MyPostListResponse extends BaseResponse {
    MyPostListResponseDto data;

    private MyPostListResponse(Boolean success, String message, MyPostListResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public static MyPostListResponse of(Boolean success, String message, MyPostListResponseDto data) {
        return new MyPostListResponse(success, message, data);
    }

    public static ResponseEntity<MyPostListResponse> newResponse(SuccessCode code, MyPostListResponseDto data) {
        MyPostListResponse response = MyPostListResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
