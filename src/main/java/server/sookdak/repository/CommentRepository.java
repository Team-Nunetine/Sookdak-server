package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.Comment;
import server.sookdak.domain.Post;
import server.sookdak.domain.User;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select distinct c.post from Comment c where c.user=?1 order by c.createdAt desc") //댓글 단 글 중복 방지
    List<Post> findAllByUserOrderByCreatedAtDesc(User user, Pageable page);
}

