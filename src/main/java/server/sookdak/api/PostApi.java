package server.sookdak.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sookdak.dto.req.PostSaveRequestDto;
import server.sookdak.dto.res.*;
import server.sookdak.service.PostService;
import server.sookdak.util.S3Util;

import javax.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static server.sookdak.constants.SuccessCode.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostApi {

    private final PostService postService;
    private final S3Util s3Util;

    @PostMapping("/{boardId}/save")
    public ResponseEntity<PostResponse> savePost(@PathVariable Long boardId,
                                                 @Valid @ModelAttribute PostSaveRequestDto postSaveRequestDto) throws IOException {

        List<String> imageURLs = new ArrayList<>();
        if (postSaveRequestDto.getImages().size() > 0) {
            imageURLs = postSaveRequestDto.getImages().stream()
                    .map(image -> {
                        try {
                            return s3Util.postUpload(image);
                        } catch (IOException e) {
                            throw new RuntimeException(e.toString());
                        }
                    })
                    .collect(Collectors.toList());
        }

        postService.savePost(postSaveRequestDto, boardId, imageURLs);

        return PostResponse.newResponse(POST_SAVE_SUCCESS);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return PostResponse.newResponse(POST_DELETE_SUCCESS);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(@PathVariable Long postId) {
        PostDetailResponseDto responseDto = postService.getPostDetail(postId);

        return PostDetailResponse.newResponse(POST_DETAIL_READ_SUCCESS, responseDto);
    }

    @GetMapping("/{order}/{boardId}/{page}")
    public ResponseEntity<PostListResponse> postList(@PathVariable Long boardId, @PathVariable String order, @PathVariable int page) {
        PostListResponseDto responseDto = postService.getPostList(boardId, order, page);

        return PostListResponse.newResponse(POST_LIST_READ_SUCCESS, responseDto);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<PostResponse> postLike(@PathVariable Long postId) {
        boolean response = postService.clickPostLike(postId);
        if (response) {
            return PostResponse.newResponse(LIKE_SUCCESS);
        } else {
            return PostResponse.newResponse(LIKE_CANCEL_SUCCESS);
        }
    }

    @PostMapping("/{postId}/scrap")
    public ResponseEntity<PostResponse> postScrap(@PathVariable Long postId){
        boolean response = postService.clickPostScrap(postId);
        if(response) {
            return PostResponse.newResponse(SCRAP_SUCCESS);
        } else{
            return PostResponse.newResponse(SCRAP_DELETE_SUCCESS);
        }
    }

}
