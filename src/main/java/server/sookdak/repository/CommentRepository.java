package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}

