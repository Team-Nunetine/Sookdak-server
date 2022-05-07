package server.sookdak.dto.res.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class ScrapListResponse extends BaseResponse {
    ScrapListResponseDto data;

    private ScrapListResponse(Boolean success, String message, ScrapListResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public static ScrapListResponse of(Boolean success, String message, ScrapListResponseDto data) {
        return new ScrapListResponse(success, message, data);
    }

    public static ResponseEntity<ScrapListResponse> newResponse(SuccessCode code, ScrapListResponseDto data) {
        ScrapListResponse response = ScrapListResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
