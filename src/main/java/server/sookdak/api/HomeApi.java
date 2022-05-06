package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.sookdak.constants.SuccessCode;
import server.sookdak.dto.res.home.HomeResponse;
import server.sookdak.dto.res.home.HomeResponseDto;
import server.sookdak.service.StarService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/home")
public class HomeApi {

    private final StarService starService;

    @GetMapping()
    public ResponseEntity<HomeResponse> home() {
        HomeResponseDto response = starService.getHome();

        return HomeResponse.newResponse(SuccessCode.HOME_INFO_SUCCESS, response);
    }
}
