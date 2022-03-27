package server.sookdak.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;
import server.sookdak.dto.TokenDto;

@Getter
@NoArgsConstructor
public class TokenResponse extends BaseResponse {
    private TokenDto data;

    private TokenResponse(Boolean success, String message, TokenDto data) {
        super(success, message);
        this.data = data;
    }

    public static ResponseEntity<TokenResponse> toResponse(SuccessCode code, TokenDto data){
        TokenResponse response = new TokenResponse(true, code.getMessage(), data);
        return new ResponseEntity(response, code.getStatus());
    }
}
