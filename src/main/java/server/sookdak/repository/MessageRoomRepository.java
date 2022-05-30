package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.MessageRoom;
import server.sookdak.domain.Post;
import server.sookdak.domain.User;

import java.util.Optional;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {

    Optional<MessageRoom> findByUserAndPost(User user, Post post);
}
