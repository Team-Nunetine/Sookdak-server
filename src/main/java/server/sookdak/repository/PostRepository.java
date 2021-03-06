package server.sookdak.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.Board;
import server.sookdak.domain.Post;
import server.sookdak.domain.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByBoardOrderByCreatedAtDescPostIdDesc(Board board, Pageable page);

    @Query("select p from Post p where p.board=?1 order by p.likes.size desc, p.createdAt desc, p.postId desc")
    List<Post> findAllByBoardOrderByLikesDescCreatedAtDesc(Board board, Pageable page);

    List<Post> findAllByUserOrderByPostIdDesc(User user, Pageable page);

    @Modifying
    @Query("delete from Post p where p.postId=?1")
    void deletePost(Long postId);

    @Query("select p from Post p where p.content like concat('%', ?1, '%') order by p.postId desc")
    List<Post> searchPost(String word, Pageable page);

    @Query("select p from Post p where p.board=?2 and p.content like concat('%', ?1, '%') order by p.postId desc")
    List<Post> searchBoardPost(String word, Board board, Pageable page);
}
