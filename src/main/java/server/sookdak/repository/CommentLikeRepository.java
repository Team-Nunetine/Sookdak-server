package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.Comment;
import server.sookdak.domain.CommentLike;
import server.sookdak.domain.User;
import server.sookdak.domain.UserCommentId;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, UserCommentId> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}
