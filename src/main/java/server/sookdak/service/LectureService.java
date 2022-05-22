package server.sookdak.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.constants.SuccessCode;
import server.sookdak.domain.Lecture;
import server.sookdak.domain.LectureId;
import server.sookdak.domain.Timetable;
import server.sookdak.domain.User;
import server.sookdak.dto.res.lecture.LectureResponseDto;
import server.sookdak.dto.res.lecture.LectureResponseDto.LectureList;
import server.sookdak.exception.CustomException;
import server.sookdak.repository.LectureRepository;
import server.sookdak.repository.TimetableRepository;
import server.sookdak.repository.UserRepository;
import server.sookdak.util.SecurityUtil;
import server.sookdak.util.WebDriverUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static server.sookdak.constants.ExceptionCode.LECTURE_NOT_FOUND;
import static server.sookdak.constants.ExceptionCode.USER_NOT_FOUND;
import static server.sookdak.constants.SuccessCode.TIMETABLE_ADD_SUCCESS;
import static server.sookdak.constants.SuccessCode.TIMETABLE_DELETE_SUCCESS;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final TimetableRepository timetableRepository;
    private final UserRepository userRepository;
    @Value("${everytime.id}")
    private String id;
    @Value("${everytime.password}")
    private String password;

    public LectureResponseDto getLectures(int page) {
        PageRequest pageRequest = PageRequest.of(page, 50);
        List<LectureList> lectures = lectureRepository.findAllLecture(pageRequest).stream()
                .map(LectureList::of)
                .collect(Collectors.toList());

        return LectureResponseDto.of(lectures);
    }

    public SuccessCode addTimetable(Long lectureId) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new CustomException(LECTURE_NOT_FOUND));

        Optional<Timetable> existTimetable = timetableRepository.findById(new LectureId(user.getUserId(), lecture.getLectureId()));
        if (existTimetable.isEmpty()) {
            Timetable timetable = Timetable.createTimetable(user, lecture);
            timetableRepository.save(timetable);
            return TIMETABLE_ADD_SUCCESS;
        }
        existTimetable.ifPresent(timetableRepository::delete);
        return TIMETABLE_DELETE_SUCCESS;
    }

    public void crawling() throws InterruptedException {
        WebDriverUtil webDriverUtil = new WebDriverUtil();
        List<Lecture> lectures = webDriverUtil.useDriver("https://everytime.kr/login", id, password);
        lectureRepository.saveAll(lectures);
    }
}
