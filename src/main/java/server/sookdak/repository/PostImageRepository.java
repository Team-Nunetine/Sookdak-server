package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.Post;
import server.sookdak.domain.PostImage;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    boolean existsByPost(Post post);

    @Modifying
    @Query("delete from PostImage pi where pi.post=?1")
    void deleteAllByPost(Post post);

    List<PostImage> findAllByPost(Post post);
}
