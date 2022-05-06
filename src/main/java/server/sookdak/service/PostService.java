package server.sookdak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.sookdak.domain.*;
import server.sookdak.dto.req.PostSaveRequestDto;
import server.sookdak.dto.res.post.MyPostListResponseDto;
import server.sookdak.dto.res.post.PostDetailResponseDto;
import server.sookdak.dto.res.post.PostDetailResponseDto.PostDetail;
import server.sookdak.dto.res.post.PostListResponseDto;
import server.sookdak.dto.res.post.ScrapListResponseDto;
import server.sookdak.exception.CustomException;
import server.sookdak.repository.*;
import server.sookdak.util.S3Util;
import server.sookdak.util.SecurityUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static server.sookdak.constants.ExceptionCode.*;
import static server.sookdak.dto.res.post.PostListResponseDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostImageRepository postImageRepository;
    private final StarRepository starRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PostScrapRepository postScrapRepository;
    private final S3Util s3Util;

    public Long savePost(PostSaveRequestDto postSaveRequestDto, Long boardId, List<String> imageURLs) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        Post post = Post.createPost(user, board, postSaveRequestDto.getContent(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));

        List<PostImage> postImages = new ArrayList<>();
        for (String imageURL : imageURLs) {
            PostImage postImage = PostImage.createPostImage(post, imageURL);
            postImageRepository.save(postImage);
            postImages.add(postImage);
        }
        post.updateImages(postImages);

        Post savedPost = postRepository.save(post);
        return savedPost.getPostId();
    }

    public void editPost(PostSaveRequestDto postSaveRequestDto, Long postId, List<String> imageURLs) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (post.getUser() != user) {
            throw new CustomException(WRITER_ONLY_EDIT);
        }

        post.updateContent(postSaveRequestDto.getContent());

        List<PostImage> postImages = new ArrayList<>();
        //원래 이미지 있을 때
        if (post.getImages().size() > 0) {
            postImageRepository.findAllByPost(post)
                    .forEach(postImage -> s3Util.delete(postImage.getUrl()));
            postImageRepository.deleteAllByPost(post);
            post.updateImages(postImages);

            if (imageURLs.size() > 0) {
                for (String imageURL : imageURLs) {
                    PostImage postImage = PostImage.createPostImage(post, imageURL);
                    postImageRepository.save(postImage);
                    postImages.add(postImage);
                }
                post.updateImages(postImages);
            }
        }

        //원래 이미지 없을 때
        if (post.getImages().size() == 0) {
            if (imageURLs.size() > 0) {
                for (String imageURL : imageURLs) {
                    PostImage postImage = PostImage.createPostImage(post, imageURL);
                    postImageRepository.save(postImage);
                    postImages.add(postImage);
                }
                post.updateImages(postImages);
            }
        }
    }


    public void deletePost(Long postId) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (post.getUser() != user) {
            throw new CustomException(WRITER_ONLY_DELETE);
        } else {
            if (post.getImages().size() > 0) {
                postImageRepository.findAllByPost(post)
                        .forEach(postImage -> s3Util.delete(postImage.getUrl()));
            }

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

        PostDetail postDetail = new PostDetail(post.getContent(), post.getCreatedAt(), post.getLikes().size(), postScrapRepository.countByPost(post), post.getComments().size(), images, post.getUser().equals(user));

        return PostDetailResponseDto.of(postDetail);
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
                    .map(PostList::of)
                    .collect(Collectors.toList());
        } else if (order.equals("popularity")) {
            posts = postRepository.findAllByBoardOrderByLikesDescCreatedAtDesc(board, pageRequest).stream()
                    .map(PostList::of)
                    .collect(Collectors.toList());
        } else {
            throw new CustomException(WRONG_TYPE_ORDER);
        }

        boolean isStar = starRepository.existsByUserAndBoard(user, board);
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

        if (post.getUser() == user) {
            throw new CustomException(SCRAP_DENIED);
        }

        if (existPostScrap.isPresent()) {
            postScrapRepository.delete(existPostScrap.get());
            return false;
        } else {
            PostScrap postScrap = PostScrap.createPostScrap(user, post);
            postScrapRepository.save(postScrap);
            return true;
        }
    }

    public MyPostListResponseDto getMyPost(int page) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 20);
        List<PostList> posts = postRepository.findAllByUserOrderByCreatedAtDescPostIdDesc(user, pageRequest).stream()
                .map(PostList::of)
                .collect(Collectors.toList());

        return MyPostListResponseDto.of(posts);
    }

    public ScrapListResponseDto getMyScrap(int page) {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 20);
        List<ScrapListResponseDto.ScrapList> scrapLists = postScrapRepository.findAllByUserOrderByCreatedAtDesc(user, pageRequest).stream()
                .map(ScrapListResponseDto.ScrapList::new)
                .collect(Collectors.toList());

        return ScrapListResponseDto.of(scrapLists);

    }
}
