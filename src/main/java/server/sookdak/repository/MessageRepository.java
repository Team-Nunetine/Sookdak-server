package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.Message;
import server.sookdak.domain.MessageRoom;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findFirstByMessageRoomOrderByCreatedAtDesc(MessageRoom messageRoom);
}
