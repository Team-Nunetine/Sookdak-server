package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.ChatRoom;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT c from ChatRoom c ORDER BY c.users.size DESC")
    List<ChatRoom> findAllDesc();
}
