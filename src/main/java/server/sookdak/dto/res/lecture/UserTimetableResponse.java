package server.sookdak.dto.res.lecture;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
public class UserTimetableResponse extends BaseResponse {
    UserTimetableResponseDto data;

    private UserTimetableResponse(Boolean success, String message, UserTimetableResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public static UserTimetableResponse of(Boolean success, String message, UserTimetableResponseDto data) {
        return new UserTimetableResponse(success, message, data);
    }

    public static ResponseEntity<UserTimetableResponse> newResponse(SuccessCode code, UserTimetableResponseDto data) {
        UserTimetableResponse response = UserTimetableResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
