package server.sookdak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.domain.*;
import server.sookdak.dto.req.CommentSaveRequestDto;
import server.sookdak.dto.res.CommentResponseDto;
import server.sookdak.exception.CustomException;
import server.sookdak.repository.*;
import server.sookdak.util.SecurityUtil;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static server.sookdak.constants.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentIdentifierRepository commentIdentifierRepository;
    private final CommentImageRepository commentImageRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentResponseDto saveComment(CommentSaveRequestDto commentSaveRequestDto, Long postId, Long parent, String imageURL) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (parent != 0) { //대댓글일 경우에
            if (commentRepository.findById(parent).isEmpty()) { //부모댓글이 존재하지 않는다면
                throw new CustomException(COMMENT_NOT_FOUND);
            } else if (commentRepository.findById(parent).get().getParent() != 0) { //부모댓글의 부모가 0이 아니라면
                throw new CustomException(RE_COMMENT_ONLY);
            }
        }

        Comment comment = Comment.createComment(user, post, parent, commentSaveRequestDto.getContent(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        if (commentSaveRequestDto.getImage() != null) {
            CommentImage commentImage = CommentImage.createCommentImage(comment, imageURL);
            commentImageRepository.save(commentImage);
        }

        commentRepository.save(comment);

        Optional<CommentIdentifier> existCommentIdentifier = commentIdentifierRepository.findByUserAndPost(user, post);
        CommentIdentifier commentIdentifier;
        if (existCommentIdentifier.isPresent()) {
            commentIdentifier = existCommentIdentifier.get();
        } else {
            commentIdentifier = CommentIdentifier.createCommentIdentifier(user, post, post.getIdentifiers().size() + 1);
            commentIdentifierRepository.save(commentIdentifier);
        }

        return CommentResponseDto.of(commentIdentifier.getCommentOrder(), comment, imageURL);
    }
}