package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {
    @Query("SELECT p FROM Board p ORDER BY p.boardId DESC")
    List<Board> findAllDesc();

    boolean existsByName(String name);
}
