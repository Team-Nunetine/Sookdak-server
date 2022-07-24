package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sookdak.dto.req.BoardSaveRequestDto;
import server.sookdak.dto.res.board.BoardListResponse;
import server.sookdak.dto.res.board.BoardListResponseDto;
import server.sookdak.dto.res.board.BoardResponse;
import server.sookdak.dto.res.board.BoardResponseDto;
import server.sookdak.service.BoardService;

import javax.validation.Valid;

import static server.sookdak.constants.SuccessCode.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardApi {
    private final BoardService boardService;

    @GetMapping()
    public ResponseEntity<BoardListResponse> findAll() {
        BoardListResponseDto responseDto = boardService.findAllDesc();

        return BoardListResponse.newResponse(BOARD_READ_SUCCESS, responseDto);
    }

    @PostMapping()
    public ResponseEntity<BoardResponse> save(@Valid @RequestBody BoardSaveRequestDto boardSaveRequestDto) {
        BoardResponseDto responseDto = boardService.saveBoard(boardSaveRequestDto);
        return BoardResponse.newResponse(BOARD_SAVE_SUCCESS,responseDto);
    }

    @DeleteMapping("/{BoardId}")
    public ResponseEntity<BoardResponse> delete(@PathVariable Long BoardId){
        BoardResponseDto responseDto = boardService.delete(BoardId);
        return BoardResponse.newResponse(BOARD_DELETE_SUCCESS,responseDto);
    }
}
