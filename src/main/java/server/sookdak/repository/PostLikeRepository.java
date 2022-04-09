package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.Post;
import server.sookdak.domain.PostLike;
import server.sookdak.domain.User;
import server.sookdak.domain.UserPostId;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, UserPostId> {

    Optional<PostLike> findByUserAndPost(User user, Post post);
}
