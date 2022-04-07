package server.sookdak.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class StarListResponse extends BaseResponse {
    StarListResponseDto data;

    private StarListResponse(Boolean success, String message, StarListResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public static StarListResponse of(Boolean success, String message, StarListResponseDto data) {
        return new StarListResponse(success, message, data);
    }

    public static ResponseEntity<StarListResponse> newResponse(SuccessCode code, StarListResponseDto data) {
        StarListResponse response = StarListResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
