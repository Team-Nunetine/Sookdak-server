package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.Board;
import server.sookdak.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByBoardOrderByCreatedAtDesc(Board board);

    List<Post> findAllByBoardOrderByLikedDescCreatedAtDesc(Board board);
}
