package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Timetable {

    @EmbeddedId
    private LectureId lectureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    @MapsId("lectureId")
    private Lecture lecture;

    public static Timetable createTimetable(User user, Lecture lecture) {
        Timetable timetable = new Timetable();
        timetable.lectureId = new LectureId(user.getUserId(), lecture.getLectureId());
        timetable.user = user;
        timetable.lecture = lecture;
        return timetable;
    }
}
