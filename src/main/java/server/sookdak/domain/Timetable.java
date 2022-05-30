package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

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

    @Enumerated(EnumType.STRING)
    private Day day1;

    @Enumerated(EnumType.STRING)
    private Day day2;

    private LocalTime startTime;

    private LocalTime endTime;

    public static Timetable createTimetable(User user, Lecture lecture, Day day1, Day day2, LocalTime startTime, LocalTime endTime) {
        Timetable timetable = new Timetable();
        timetable.lectureId = new LectureId(user.getUserId(), lecture.getLectureId());
        timetable.user = user;
        timetable.lecture = lecture;
        timetable.day1 = day1;
        timetable.day2 = day2;
        timetable.startTime = startTime;
        timetable.endTime = endTime;
        return timetable;
    }
}
