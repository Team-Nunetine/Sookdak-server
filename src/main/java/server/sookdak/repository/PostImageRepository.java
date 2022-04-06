package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
