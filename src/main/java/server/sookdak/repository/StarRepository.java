package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.Board;
import server.sookdak.domain.Star;
import server.sookdak.domain.StarId;
import server.sookdak.domain.User;

import java.util.List;
import java.util.Optional;

public interface StarRepository extends JpaRepository<Star, StarId> {

    Optional<Star> findByUserAndBoard(User user, Board board);

    boolean existsByUserAndBoard(User user, Board board);

    List<Star> findAllByUserOrderByCreatedAtAsc(User user);
}
