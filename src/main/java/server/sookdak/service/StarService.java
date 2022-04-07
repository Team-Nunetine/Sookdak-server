package server.sookdak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.domain.Board;
import server.sookdak.domain.Star;
import server.sookdak.domain.User;
import server.sookdak.exception.CustomException;
import server.sookdak.repository.BoardRepository;
import server.sookdak.repository.StarRepsository;
import server.sookdak.repository.UserRepository;
import server.sookdak.util.SecurityUtil;

import java.util.Optional;

import static server.sookdak.constants.ExceptionCode.BOARD_NOT_FOUND;
import static server.sookdak.constants.ExceptionCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class StarService {

    private final StarRepsository starRepsository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public boolean clickStar(Long boardId) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        Optional<Star> existStar = starRepsository.findByUserAndBoard(user, board);
        if (existStar.isPresent()) {
            starRepsository.delete(existStar.get());
            return false;
        } else {
            Star star = Star.createStar(user, board);
            starRepsository.save(star);
            return true;
        }
    }
}
