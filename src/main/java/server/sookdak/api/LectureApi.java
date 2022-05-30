package server.sookdak.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.res.lecture.*;
import server.sookdak.service.LectureService;

import java.util.Map;

import static server.sookdak.constants.SuccessCode.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lecture")
public class LectureApi {

    private final LectureService lectureService;

    @GetMapping("/{page}")
    public ResponseEntity<LectureResponse> getLectures(@PathVariable int page) {
        LectureResponseDto response = lectureService.getLectures(page);

        return LectureResponse.newResponse(LECTURE_READ_SUCCESS, response);
    }

    @PostMapping("/search/{page}")
    public ResponseEntity<LectureResponse> searchLecture(@RequestBody Map<String, String> word, @PathVariable int page) {
        LectureResponseDto response = lectureService.searchLecture(word.get("word"), page);

        return LectureResponse.newResponse(LECTURE_SEARCH_SUCCESS, response);
    }

    @PostMapping("/{lectureId}")
    public ResponseEntity<TimetableResponse> userTimetable(@PathVariable Long lectureId) {
        SuccessCode successCode = lectureService.addTimetable(lectureId);

        return TimetableResponse.newResponse(successCode);
    }

    @GetMapping("")
    public ResponseEntity<UserTimetableResponse> getTimetable() {
        UserTimetableResponseDto response = lectureService.getTimetable();

        return UserTimetableResponse.newResponse(TIMETABLE_READ_SUCCESS, response);
    }

    @PostMapping("/crawl")
    public void crawling() throws InterruptedException {
        lectureService.crawling();
    }
}
