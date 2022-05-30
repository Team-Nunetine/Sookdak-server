package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.CommentIdentifier;
import server.sookdak.domain.Post;
import server.sookdak.domain.User;
import server.sookdak.domain.UserPostId;

import java.util.Optional;

public interface CommentIdentifierRepository extends JpaRepository<CommentIdentifier, UserPostId> {

    Optional<CommentIdentifier> findByUserAndPost(User user, Post post);

    @Query("select ci.commentOrder from CommentIdentifier ci where ci.user=?1 and ci.post=?2")
    int getCommentOrder(User user, Post post);

}
