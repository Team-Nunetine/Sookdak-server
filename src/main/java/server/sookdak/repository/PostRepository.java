package server.sookdak.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.Board;
import server.sookdak.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByBoardOrderByCreatedAtDescPostIdDesc(Board board, Pageable page);

    @Query("select p from Post p where p.board=?1 order by p.likes.size desc, p.createdAt desc, p.postId desc")
    List<Post> findAllByBoardOrderByLikesDescCreatedAtDesc(Board board, Pageable page);
}
