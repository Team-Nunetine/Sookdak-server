package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.CommentIdentifier;
import server.sookdak.domain.Post;
import server.sookdak.domain.User;
import server.sookdak.domain.UserPostId;

import java.util.Optional;

public interface CommentIdentifierRepository extends JpaRepository<CommentIdentifier, UserPostId> {

    Optional<CommentIdentifier> findByUserAndPost(User user, Post post);

}
