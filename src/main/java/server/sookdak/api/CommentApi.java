package server.sookdak.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sookdak.dto.req.CommentSaveRequestDto;
import server.sookdak.dto.res.CommentResponse;
import server.sookdak.dto.res.CommentResponseDto;
import server.sookdak.service.CommentService;
import server.sookdak.util.S3Util;

import javax.validation.Valid;
import java.io.IOException;

import static server.sookdak.constants.SuccessCode.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentApi {

    private final CommentService commentService;
    private final S3Util s3Util;

    @PostMapping("/{postId}/{parent}/save")
    public ResponseEntity<CommentResponse> saveComment(@PathVariable Long postId, @PathVariable Long parent, @Valid @ModelAttribute CommentSaveRequestDto commentSaveRequestDto) throws IOException {
        String imageURL = null;
        if (commentSaveRequestDto.getImage() != null) {
            imageURL = s3Util.commentUpload(commentSaveRequestDto.getImage());
        }
        CommentResponseDto responseDto = commentService.saveComment(commentSaveRequestDto, postId, parent,imageURL);
        return CommentResponse.newResponse(COMMENT_SAVE_SUCCESS, responseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return CommentResponse.newDeleteResponse(COMMENT_DELETE_SUCCESS);
    }
}