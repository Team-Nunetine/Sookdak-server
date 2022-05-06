package server.sookdak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.domain.Board;
import server.sookdak.domain.Star;
import server.sookdak.domain.User;
import server.sookdak.dto.res.home.HomeResponseDto;
import server.sookdak.dto.res.home.HomeResponseDto.StarBoardList;
import server.sookdak.dto.res.board.StarListResponseDto;
import server.sookdak.exception.CustomException;
import server.sookdak.repository.BoardRepository;
import server.sookdak.repository.PostRepository;
import server.sookdak.repository.StarRepository;
import server.sookdak.repository.UserRepository;
import server.sookdak.util.SecurityUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static server.sookdak.constants.ExceptionCode.BOARD_NOT_FOUND;
import static server.sookdak.constants.ExceptionCode.USER_NOT_FOUND;
import static server.sookdak.dto.res.board.StarListResponseDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class StarService {

    private final StarRepository starRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    public boolean clickStar(Long boardId) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        Optional<Star> existStar = starRepository.findByUserAndBoard(user, board);
        if (existStar.isPresent()) {
            starRepository.delete(existStar.get());
            return false;
        } else {
            Star star = Star.createStar(user, board);
            starRepository.save(star);
            return true;
        }
    }

    public StarListResponseDto getStarList() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        List<StarList> stars = starRepository.findAllByUserOrderByCreatedAtAsc(user).stream()
                .map(StarList::new)
                .collect(Collectors.toList());

        return StarListResponseDto.of(stars);
    }

    public HomeResponseDto getHome() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(0, 3);

        List<StarBoardList> starBoardLists = starRepository.findAllByUserOrderByCreatedAtAsc(user).stream()
                .map(star -> new StarBoardList(star, postRepository.findAllByBoardOrderByCreatedAtDescPostIdDesc(star.getBoard(), pageRequest)))
                .collect(Collectors.toList());

        return HomeResponseDto.of(starBoardLists);
    }
}
