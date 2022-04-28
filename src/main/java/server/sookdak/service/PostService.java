package server.sookdak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.domain.*;
import server.sookdak.dto.req.PostSaveRequestDto;
import server.sookdak.dto.res.PostDetailResponseDto;
import server.sookdak.dto.res.PostDetailResponseDto.PostDetail;
import server.sookdak.dto.res.PostListResponseDto;
import server.sookdak.exception.CustomException;
import server.sookdak.repository.*;
import server.sookdak.util.SecurityUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static server.sookdak.constants.ExceptionCode.*;
import static server.sookdak.dto.res.PostListResponseDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostImageRepository postImageRepository;
    private final StarRepsository starRepsository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PostScrapRepository postScrapRepository;

    public void savePost(PostSaveRequestDto postSaveRequestDto, Long boardId, List<String> imageURLs) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        Post post = Post.createPost(user, board, postSaveRequestDto.getContent(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));

        for (String imageURL : imageURLs) {
            PostImage postImage = PostImage.createPostImage(post, imageURL);
            postImageRepository.save(postImage);
        }

        postRepository.save(post);
    }


    public void deletePost(Long postId) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (post.getUser() != user) {
            throw new CustomException(WRITER_ONLY);
        } else {
            postRepository.delete(post);
        }
    }

    public PostDetailResponseDto getPostDetail(Long postId) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        List<String> images = new ArrayList<>();
        for (PostImage image : post.getImages()) {
            images.add(image.getUrl());
        }

        PostDetail postDetail = new PostDetail(post.getContent(), post.getCreatedAt(), post.getLikes().size(), postScrapRepository.countByPost(post), images, post.getUser().equals(user));

        return PostDetailResponseDto.of(postDetail, null);
    }

    public PostListResponseDto getPostList(Long boardId, String order, int page) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        List<PostList> posts = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, 20);
        if (order.equals("latest")) {
            posts = postRepository.findAllByBoardOrderByCreatedAtDescPostIdDesc(board, pageRequest).stream()
                    .map(post -> new PostList(post, post.getImages().size() != 0, post.getLikes().size()))
                    .collect(Collectors.toList());
        } else if (order.equals("popularity")) {
            posts = postRepository.findAllByBoardOrderByLikesDescCreatedAtDesc(board, pageRequest).stream()
                    .map(post -> new PostList(post, post.getImages().size() != 0, post.getLikes().size()))
                    .collect(Collectors.toList());
        } else {
            throw new CustomException(WRONG_TYPE_ORDER);
        }

        boolean isStar = starRepsository.existsByUserAndBoard(user, board);
        return PostListResponseDto.of(isStar, posts);
    }

    public boolean clickPostLike(Long postId) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (post.getUser() == user) {
            throw new CustomException(LIKE_DENIED);
        }

        Optional<PostLike> existPostLike = postLikeRepository.findByUserAndPost(user, post);
        if (existPostLike.isPresent()) {
            postLikeRepository.delete(existPostLike.get());
            return false;
        } else {
            PostLike postLike = PostLike.createPostLike(user, post);
            postLikeRepository.save(postLike);
            return true;
        }
    }

    public boolean clickPostScrap(Long postId) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        Optional<PostScrap> existPostScrap = postScrapRepository.findByUserAndPost(user, post);
        if (existPostScrap.isPresent()) {
            postScrapRepository.delete(existPostScrap.get());
            return false;
        } else {
            PostScrap postScrap = PostScrap.createPostScrap(user, post);
            postScrapRepository.save(postScrap);
            return true;
        }
    }
}
