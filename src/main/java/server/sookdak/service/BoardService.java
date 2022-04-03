package server.sookdak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.domain.Board;
import server.sookdak.domain.User;
import server.sookdak.dto.req.BoardSaveRequestDto;
import server.sookdak.dto.res.BoardListResponseDto;
import server.sookdak.repository.BoardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<BoardListResponseDto> findAllDesc(){
        return boardRepository.findAllDesc().stream()
                .map(BoardListResponseDto::new)
                .collect(Collectors.toList());
    }


    public Long SaveBoard(Long userId, BoardSaveRequestDto boardSaveRequestDto){
        User user = userService.findById(userId);
        Board board = Board.builder()
                .name(boardSaveRequestDto.getName())
                .description((boardSaveRequestDto.getDescription()))
                .build();
        Board savedBoard = boardRepository.save(board);
        user.createBoard(savedBoard);
        return savedBoard.getBoardId();

    }

}
