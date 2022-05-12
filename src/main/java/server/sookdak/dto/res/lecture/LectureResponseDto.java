package server.sookdak.dto.res.lecture;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.Lecture;

import java.util.List;

@Getter
@NoArgsConstructor
public class LectureResponseDto {
    private List<LectureList> lectures;

    public LectureResponseDto(List<LectureList> lectures) {
        this.lectures = lectures;
    }

    public static LectureResponseDto of(List<LectureList> lectures) {
        return new LectureResponseDto(lectures);
    }

    @Getter
    @NoArgsConstructor
    public static class LectureList {
        private Long lectureId;
        private String name;
        private String professor;
        private String classNum;
        private String datetime;
        private String place;
        private String type;
        private int credit;
        private int studentNum;
        private String info;

        public static LectureList of(Lecture lecture) {
            LectureList lectureList = new LectureList();
            lectureList.lectureId = lecture.getLectureId();
            lectureList.name = lecture.getName();
            lectureList.professor = lecture.getProfessor();
            lectureList.classNum = lecture.getClassNum();
            lectureList.datetime = lecture.getDatetime();
            lectureList.place = lecture.getPlace();
            lectureList.type = lecture.getType();
            lectureList.credit = lecture.getCredit();
            lectureList.studentNum = lecture.getStudentNum();
            lectureList.info = lecture.getInfo();
            return lectureList;
        }
    }

}
