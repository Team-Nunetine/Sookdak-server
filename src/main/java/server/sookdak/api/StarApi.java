package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sookdak.dto.res.board.StarListResponse;
import server.sookdak.dto.res.board.StarListResponseDto;
import server.sookdak.dto.res.board.StarResponse;
import server.sookdak.service.StarService;

import static server.sookdak.constants.SuccessCode.*;

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

    @GetMapping()
    public ResponseEntity<StarListResponse> starList() {
        StarListResponseDto responseDto = starService.getStarList();

        return StarListResponse.newResponse(STAR_INFO_SUCCESS, responseDto);
    }
}
