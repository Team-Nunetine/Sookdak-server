package server.sookdak.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.Message;
import server.sookdak.domain.MessageRoom;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findFirstByMessageRoomOrderByCreatedAtDesc(MessageRoom messageRoom);

    List<Message> findByMessageRoomOrderByCreatedAtDesc(MessageRoom messageRoom, Pageable page);
}