package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
