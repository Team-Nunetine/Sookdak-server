package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.Post;
import server.sookdak.domain.PostWarn;
import server.sookdak.domain.User;
import server.sookdak.domain.UserPostId;

import java.util.Optional;

public interface PostWarnRepository extends JpaRepository<PostWarn, UserPostId> {
    Optional<PostWarn> findByUserAndPost(User user, Post post);

    int countByPost(Post post);
}
