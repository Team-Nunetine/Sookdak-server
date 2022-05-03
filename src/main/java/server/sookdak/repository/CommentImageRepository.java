package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.CommentImage;

public interface CommentImageRepository extends JpaRepository<CommentImage, Long>{
}
