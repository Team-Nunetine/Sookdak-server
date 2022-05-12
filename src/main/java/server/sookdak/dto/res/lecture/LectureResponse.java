package server.sookdak.dto.res.lecture;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
public class LectureResponse extends BaseResponse {
    LectureResponseDto data;

    private LectureResponse(Boolean success, String message, LectureResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public static LectureResponse of(Boolean success, String message, LectureResponseDto data) {
        return new LectureResponse(success, message, data);
    }

    public static ResponseEntity<LectureResponse> newResponse(SuccessCode code, LectureResponseDto data) {
        LectureResponse response = LectureResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
