package server.sookdak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.Day;
import server.sookdak.domain.LectureId;
import server.sookdak.domain.Timetable;
import server.sookdak.domain.User;

import java.time.LocalTime;
import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, LectureId> {

    @Query("select t from Timetable t where (t.user = ?5) and (t.day1=?1 or t.day1=?2 or t.day2=?1 or t.day2=?2) and ((t.startTime < ?4 and t.endTime >= ?4) or (t.endTime > ?3 and t.startTime <= ?3) or (t.startTime >= ?3 and t.endTime <=?4))")
    List<Timetable> getTimetableWithDatetime(Day day1, Day day2, LocalTime startTime, LocalTime endTime, User user);

    List<Timetable> findAllByUser(User user);
}
