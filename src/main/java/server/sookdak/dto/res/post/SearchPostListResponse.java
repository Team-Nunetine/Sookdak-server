package server.sookdak.dto.res.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.BaseResponse;

@Getter
@NoArgsConstructor
public class SearchPostListResponse extends BaseResponse {
    SearchPostListResponseDto data;

    private SearchPostListResponse(Boolean success, String message, SearchPostListResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public static SearchPostListResponse of(Boolean success, String message, SearchPostListResponseDto data) {
        return new SearchPostListResponse(success, message, data);
    }

    public static ResponseEntity<SearchPostListResponse> newResponse(SuccessCode code, SearchPostListResponseDto data) {
        SearchPostListResponse response = SearchPostListResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<>(response, code.getStatus());
    }
}
