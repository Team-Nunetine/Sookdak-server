package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sookdak.domain.LectureId;
import server.sookdak.domain.Timetable;

public interface TimetableRepository extends JpaRepository<Timetable, LectureId> {
}
