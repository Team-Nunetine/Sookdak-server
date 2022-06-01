package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.Comment;
import server.sookdak.domain.Post;
import server.sookdak.domain.User;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select distinct c.post from Comment c where c.user=?1 order by c.createdAt desc")
    List<Post> findAllByUserOrderByCreatedAtDesc(User user, Pageable page);

    @Query("select c from Comment c where c.post= ?1 and c.parent = 0")
    List<Comment> findAllByPost(Post post);

    @Query("select c from Comment c where c.parent = ?1")
    List<Comment> findAllByParent(Long parent);

    @Query("select distinct c.parent from Comment c where c.parent <> 0 and c.post=?1")
    List<Long> findReplies(Post post);

    @Query("select count(c) from Comment c where c.commentId=?1")
    int countReplyParent(Long parent);
}

