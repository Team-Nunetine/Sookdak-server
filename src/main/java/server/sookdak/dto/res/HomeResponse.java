package server.sookdak.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class HomeResponse extends BaseResponse {
    HomeResponseDto data;

    private HomeResponse(Boolean success, String message, HomeResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public static HomeResponse of(Boolean success, String message, HomeResponseDto data) {
        return new HomeResponse(success, message, data);
    }

    public static ResponseEntity<HomeResponse> newResponse(SuccessCode code, HomeResponseDto data) {
        HomeResponse response = HomeResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
