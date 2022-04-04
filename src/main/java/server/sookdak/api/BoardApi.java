package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.req.BoardSaveRequestDto;
import server.sookdak.dto.res.BoardListResponseDto;
import server.sookdak.dto.res.BoardResponse;
import server.sookdak.dto.res.UserResponse;
import server.sookdak.service.BoardService;

import javax.validation.Valid;
import java.util.List;

import static server.sookdak.constants.SuccessCode.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardApi {
    private final BoardService boardService;

    @GetMapping()
    public List<BoardListResponseDto> findAll() {
        return boardService.findAllDesc();
    }

    @PostMapping("/save")
    public ResponseEntity<BoardResponse> save(@Valid @RequestBody BoardSaveRequestDto boardSaveRequestDto) {
        boardService.saveBoard(boardSaveRequestDto);

        return BoardResponse.newResponse(BOARD_SAVE_SUCCESS);
    }
}
