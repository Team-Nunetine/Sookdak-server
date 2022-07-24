package server.sookdak.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sookdak.dto.req.CommentSaveRequestDto;
import server.sookdak.dto.res.comment.*;
import server.sookdak.service.CommentService;
import server.sookdak.util.S3Util;

import javax.validation.Valid;
import java.io.IOException;

import static server.sookdak.constants.SuccessCode.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentApi {

    private final CommentService commentService;
    private final S3Util s3Util;

    @PostMapping("/{postId}/{parent}")
    public ResponseEntity<CommentDetailResponse> saveComment(@PathVariable Long postId, @PathVariable Long parent, @Valid @ModelAttribute CommentSaveRequestDto commentSaveRequestDto) throws IOException {
        String imageURL = null;
        if (commentSaveRequestDto.getImage() != null) {
            imageURL = s3Util.commentUpload(commentSaveRequestDto.getImage());
        }
        CommentDetailResponseDto responseDto = commentService.saveComment(commentSaveRequestDto, postId, parent, imageURL);
        return CommentDetailResponse.newResponse(COMMENT_SAVE_SUCCESS, responseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return CommentResponse.newResponse(COMMENT_DELETE_SUCCESS);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommentListResponse> getCommentDetail(@PathVariable Long postId) {
        CommentListResponseDto responseDto = commentService.findAll(postId);

        return CommentListResponse.newResponse(COMMENT_LIST_READ_SUCCESS, responseDto);
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<CommentResponse> commentLike(@PathVariable Long commentId) {
        boolean response = commentService.clickCommentLike(commentId);
        if(response) {
            return CommentResponse.newResponse(LIKE_SUCCESS);
        } else{
            return CommentResponse.newResponse(LIKE_CANCEL_SUCCESS);
        }

    }
}