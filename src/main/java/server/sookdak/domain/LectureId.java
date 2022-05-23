package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureId implements Serializable {
    private Long userId;
    private Long lectureId;

    public LectureId(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
    }
}
