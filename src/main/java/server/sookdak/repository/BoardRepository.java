package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.Board;
import server.sookdak.domain.User;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    @Query("SELECT p FROM Board p ORDER BY p.stars.size DESC")
    List<Board> findAllDesc();

    List<Board> findByUser(User user);

    boolean existsByName(String name);
}
