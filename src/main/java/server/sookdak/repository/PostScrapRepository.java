package server.sookdak.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.Post;
import server.sookdak.domain.PostScrap;
import server.sookdak.domain.User;
import server.sookdak.domain.UserPostId;

import java.util.List;
import java.util.Optional;

public interface PostScrapRepository extends JpaRepository<PostScrap, UserPostId> {
    Optional<PostScrap> findByUserAndPost(User user, Post post);

    int countByPost(Post post);

    List<PostScrap> findAllByUserOrderByCreatedAtDesc(User user, Pageable page);

    boolean existsByUserAndPost(User user, Post post);
}
