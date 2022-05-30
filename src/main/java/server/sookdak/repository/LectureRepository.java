package server.sookdak.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sookdak.domain.Lecture;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("select l from Lecture l")
    List<Lecture> findAllLecture(Pageable page);

    @Query("select l from Lecture l where l.name like concat('%', ?1 ,'%') or l.professor like concat('%', ?1, '%') or l.place like concat('%', ?1, '%')")
    List<Lecture> searchLecture(String word, Pageable page);
}
