package server.sookdak.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.domain.Lecture;
import server.sookdak.dto.res.lecture.LectureResponseDto;
import server.sookdak.dto.res.lecture.LectureResponseDto.LectureList;
import server.sookdak.repository.LectureRepository;
import server.sookdak.util.WebDriverUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
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

    public void crawling() throws InterruptedException {
        WebDriverUtil webDriverUtil = new WebDriverUtil();
        List<Lecture> lectures = webDriverUtil.useDriver("https://everytime.kr/login", id, password);
        lectureRepository.saveAll(lectures);
    }
}
