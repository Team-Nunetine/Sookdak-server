package server.sookdak.dto.res.lecture;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Lecture;
import server.sookdak.domain.Timetable;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserTimetableResponseDto {
    private List<UserTimetable> lectures;

    private UserTimetableResponseDto(List<UserTimetable> lectures) {
        this.lectures = lectures;
    }

    public static UserTimetableResponseDto of(List<UserTimetable> lectures) {
        return new UserTimetableResponseDto(lectures);
    }

    @Getter
    @NoArgsConstructor
    public static class UserTimetable {
        private Long lectureId;
        private String name;
        private String professor;
        private String classNum;

        private String day1;
        private String day2;

        @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime startTime;

        @JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime endTime;

        private String place;
        private String type;
        private int credit;
        private int studentNum;
        private String info;

        public static UserTimetable of(Timetable timetable) {
            UserTimetable userTimetable = new UserTimetable();
            Lecture lecture = timetable.getLecture();
            userTimetable.lectureId = lecture.getLectureId();
            userTimetable.name = lecture.getName();
            userTimetable.professor = lecture.getProfessor();
            userTimetable.classNum = lecture.getClassNum();
            if (timetable.getDay1() != null) {
                userTimetable.day1 = timetable.getDay1().getDay();
            }
            if (timetable.getDay2() != null) {
                userTimetable.day2 = timetable.getDay2().getDay();
            }
            userTimetable.startTime = timetable.getStartTime();
            userTimetable.endTime = timetable.getEndTime();
            if (lecture.getPlace().equals("")) {
                userTimetable.place = null;
            } else {
                userTimetable.place = lecture.getPlace();
            }
            userTimetable.type = lecture.getType();
            userTimetable.credit = lecture.getCredit();
            userTimetable.studentNum = lecture.getStudentNum();
            userTimetable.info = lecture.getInfo();
            return userTimetable;
        }

    }
}
