package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
