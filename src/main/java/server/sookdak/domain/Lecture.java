package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static Lecture createLecture(String name, String professor, String classNum, String datetime, String place, String type, int credit, int studentNum, String info) {
        Lecture lecture = new Lecture();
        lecture.name = name;
        lecture.professor = professor;
        lecture.classNum = classNum;
        lecture.datetime = datetime;
        lecture.place = place;
        lecture.type = type;
        lecture.credit = credit;
        lecture.studentNum = studentNum;
        lecture.info = info;
        return lecture;
    }
}
