package server.sookdak.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.constants.SuccessCode;
import server.sookdak.domain.*;
import server.sookdak.dto.res.lecture.LectureResponseDto;
import server.sookdak.dto.res.lecture.LectureResponseDto.LectureList;
import server.sookdak.dto.res.lecture.UserTimetableResponseDto;
import server.sookdak.dto.res.lecture.UserTimetableResponseDto.UserTimetable;
import server.sookdak.exception.CustomException;
import server.sookdak.repository.LectureRepository;
import server.sookdak.repository.TimetableRepository;
import server.sookdak.repository.UserRepository;
import server.sookdak.util.SecurityUtil;
import server.sookdak.util.WebDriverUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static server.sookdak.constants.ExceptionCode.*;
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
            // 요일, 시간 파싱해서 저장
            String datetime = lecture.getDatetime();
            Day day1 = null, day2 = null;
            LocalTime startTime = null, endTime = null;

            int i = 0;
            if (!datetime.equals("")) {
                while (true) {
                    char c = datetime.charAt(i);
                    if (Character.isDigit(c)) {
                        break;
                    }
                    if (i == 0) {
                        day1 = Day.nameOf(Character.toString(c));
                    }
                    if (i == 1) {
                        day2 = Day.nameOf(Character.toString(c));
                    }
                    i++;
                }
                startTime = LocalTime.of(Integer.parseInt(datetime.substring(i, i + 2)), Integer.parseInt(datetime.substring(i + 3, i + 5)));
                endTime = LocalTime.of(Integer.parseInt(datetime.substring(i + 6, i + 8)), Integer.parseInt(datetime.substring(i + 9, i + 11)));

                // 추가된 강의 중 겹치는 시간 찾기
                List<Timetable> timetables = timetableRepository.getTimetableWithDatetime(day1, day2, startTime, endTime, user);
                if (timetables.size() > 0) {
                    throw new CustomException(DUPLICATE_LECTURE_DATETIME);
                }
            }
            Timetable timetable = Timetable.createTimetable(user, lecture, day1, day2, startTime, endTime);
            timetableRepository.save(timetable);
            return TIMETABLE_ADD_SUCCESS;
        }
        existTimetable.ifPresent(timetableRepository::delete);
        return TIMETABLE_DELETE_SUCCESS;
    }

    public UserTimetableResponseDto getTimetable() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        List<UserTimetable> userTimetable = timetableRepository.findAllByUser(user).stream()
                .map(UserTimetable::of)
                .collect(Collectors.toList());

        return UserTimetableResponseDto.of(userTimetable);
    }

    public void crawling() throws InterruptedException {
        WebDriverUtil webDriverUtil = new WebDriverUtil();
        List<Lecture> lectures = webDriverUtil.useDriver("https://everytime.kr/login", id, password);
        lectureRepository.saveAll(lectures);
    }
}
