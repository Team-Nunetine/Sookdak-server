package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import server.sookdak.dto.req.BoardSaveRequestDto;
import server.sookdak.dto.res.BoardListResponseDto;
import server.sookdak.service.BoardService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardApi {
    private final BoardService boardService;

    @GetMapping()
    public List<BoardListResponseDto> findAll(){
        return boardService.findAllDesc();
    }

    @PostMapping("/{userId}/save")
    public Long save(@PathVariable Long userId, @RequestBody BoardSaveRequestDto boardSaveRequestDto){
        return boardService.SaveBoard(userId, boardSaveRequestDto);
    }
}
