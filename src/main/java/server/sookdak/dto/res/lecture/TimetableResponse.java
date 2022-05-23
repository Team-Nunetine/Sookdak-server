package server.sookdak.dto.res.lecture;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
public class TimetableResponse extends BaseResponse {

    private TimetableResponse(Boolean success, String message) {
        super(success, message);
    }

    public static TimetableResponse of(Boolean success, String message) {
        return new TimetableResponse(success, message);
    }

    public static ResponseEntity<TimetableResponse> newResponse(SuccessCode code) {
        return new ResponseEntity<>(TimetableResponse.of(true, code.getMessage()), code.getStatus());
    }
}
