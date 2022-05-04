package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.Comment;
import server.sookdak.domain.CommentImage;

import java.util.Optional;

public interface CommentImageRepository extends JpaRepository<CommentImage, Long>{
    Optional<CommentImage> findByComment(Comment comment);

}
