package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.MessageRoom;
import server.sookdak.domain.Post;
import server.sookdak.domain.User;

import java.util.List;
import java.util.Optional;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {

    Optional<MessageRoom> findByUserAndPost(User user, Post post);

    List<MessageRoom> findByUser(User user);

    @Query("select r from MessageRoom r join fetch Post p on r.post=p where p.user=?1")
    List<MessageRoom> findByUserPost(User user);
}
