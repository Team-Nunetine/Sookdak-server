package server.sookdak.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class UserResponse extends BaseResponse {
    private UserResponseDto data;

    private UserResponse(Boolean success, String message, UserResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public static ResponseEntity<UserResponseDto> toResponse(SuccessCode code, UserResponseDto data) {
        UserResponse response = new UserResponse(true, code.getMessage(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
