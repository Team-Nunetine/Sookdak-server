package server.sookdak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.domain.Board;
import server.sookdak.domain.User;
import server.sookdak.dto.req.BoardSaveRequestDto;
import server.sookdak.dto.res.BoardListResponseDto;
import server.sookdak.dto.res.BoardListResponseDto.BoardList;
import server.sookdak.dto.res.BoardResponseDto;
import server.sookdak.exception.CustomException;
import server.sookdak.repository.BoardRepository;
import server.sookdak.repository.UserRepository;
import server.sookdak.util.SecurityUtil;

import java.util.List;
import java.util.stream.Collectors;

import static server.sookdak.constants.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public BoardListResponseDto findAllDesc() {
        List<BoardList> boards = boardRepository.findAllDesc().stream()
                .map(BoardList::new)
                .collect(Collectors.toList());
        return BoardListResponseDto.of(boards);
    }

    @Transactional(readOnly = true)
    public BoardListResponseDto findByUser(){
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        List<BoardList> boards = boardRepository.findByUser(user).stream()
                .map(BoardList::new)
                .collect(Collectors.toList());
        return BoardListResponseDto.of(boards);
    }


    public BoardResponseDto saveBoard(BoardSaveRequestDto boardSaveRequestDto) {
        // 현재 로그인한 유저 찾기
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // board 객체 생성
        Board board = Board.createBoard(user, boardSaveRequestDto.getName(), boardSaveRequestDto.getDescription());

        // board 저장
        if (boardRepository.existsByName(boardSaveRequestDto.getName())) {
            // 이름 중복 시 에러 처리
            throw new CustomException(DUPLICATE_BOARD_NAME);
        } else {
            boardRepository.save(board);
        }
        return BoardResponseDto.of(board);
    }

    public BoardResponseDto delete(Long BoardId) {
        Board board = boardRepository.findById(BoardId)
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));
        boardRepository.delete(board);
        return BoardResponseDto.of(board);
    }
    }


