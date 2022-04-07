package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.res.StarResponse;
import server.sookdak.service.StarService;

import static server.sookdak.constants.SuccessCode.STAR_CANCEL_SUCCESS;
import static server.sookdak.constants.SuccessCode.STAR_SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/star")
public class StarApi {

    private final StarService starService;

    @PostMapping("/{boardId}")
    public ResponseEntity<StarResponse> star(@PathVariable Long boardId) {
        boolean response = starService.clickStar(boardId);
        if (response) {
            return StarResponse.newResponse(STAR_SUCCESS);
        } else {
            return StarResponse.newResponse(STAR_CANCEL_SUCCESS);
        }
    }
}
