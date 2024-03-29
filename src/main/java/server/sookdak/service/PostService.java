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
import server.sookdak.dto.res.post.SearchPostListResponseDto;
import server.sookdak.dto.res.post.SearchPostListResponseDto.SearchPostList;
import server.sookdak.exception.CustomException;
import server.sookdak.repository.*;
import server.sookdak.util.S3Util;
import server.sookdak.util.SecurityUtil;

import java.time.LocalDateTime;
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
    private final PostWarnRepository postWarnRepository;
    private final StarRepository starRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PostScrapRepository postScrapRepository;
    private final CommentRepository commentRepository;
    private final S3Util s3Util;

    public Long savePost(PostSaveRequestDto postSaveRequestDto, Long boardId, List<String> imageURLs) {
        User user = getUser();

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        Post post = Post.createPost(user, board, postSaveRequestDto.getContent(), LocalDateTime.now());

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
        User user = getUser();

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
        User user = getUser();

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
        User user = getUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        List<String> images = new ArrayList<>();
        for (PostImage image : post.getImages()) {
            images.add(image.getUrl());
        }

        boolean userLiked = postLikeRepository.existsByUserAndPost(user, post);

        boolean userScrapped = postScrapRepository.existsByUserAndPost(user, post);

        PostDetail postDetail = new PostDetail(post.getContent(), post.getCreatedAt(), post.getLikes().size(), postScrapRepository.countByPost(post), post.getComments().size(), images, post.getUser().equals(user), userLiked, userScrapped);

        return PostDetailResponseDto.of(postDetail);
    }

    public PostListResponseDto getPostList(Long boardId, String order, int page) {
        User user = getUser();

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
        User user = getUser();

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
        User user = getUser();

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
        User user = getUser();

        PageRequest pageRequest = PageRequest.of(page, 20);
        List<PostList> posts = postRepository.findAllByUserOrderByPostIdDesc(user, pageRequest).stream()
                .map(PostList::of)
                .collect(Collectors.toList());

        return MyPostListResponseDto.of(posts);
    }

    public MyPostListResponseDto getMyScrap(int page) {
        User user = getUser();

        PageRequest pageRequest = PageRequest.of(page, 20);
        List<PostList> scrapList = postScrapRepository.findAllByUserOrderByCreatedAtDesc(user, pageRequest).stream()
                .map(postScrap -> PostList.of(postScrap.getPost()))
                .collect(Collectors.toList());

        return MyPostListResponseDto.of(scrapList);

    }

    public MyPostListResponseDto getMyComment(int page) {
        User user = getUser();

        PageRequest pageRequest = PageRequest.of(page, 20);
        List<PostList> commentList = commentRepository.findAllByUserOrderByCreatedAtDesc(user, pageRequest).stream()
                .map(PostList::of)
                .collect(Collectors.toList());

        return MyPostListResponseDto.of(commentList);
    }

    public void postWarn(Long postId) {
        User user = getUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        if (post.getUser() == user) {
            throw new CustomException(WARN_DENIED);
        }

        Optional<PostWarn> existPostWarn = postWarnRepository.findByUserAndPost(user, post);
        if (existPostWarn.isPresent()) {
            throw new CustomException(ALREADY_WARN);
        }
        PostWarn postWarn = PostWarn.createPostWarn(user, post);
        postWarnRepository.save(postWarn);

        int count = postWarnRepository.countByPost(post);
        if (count == 5) {
            postRepository.deletePost(post.getPostId());
        }
    }

    public SearchPostListResponseDto search(String word, Long boardId, int page) {
        User user = getUser();

        PageRequest pageRequest = PageRequest.of(page, 20);

        if (boardId == 0) {
            List<SearchPostList> posts = postRepository.searchPost(word, pageRequest).stream()
                    .map(SearchPostList::of)
                    .collect(Collectors.toList());
            return SearchPostListResponseDto.of(posts);
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));
        List<SearchPostList> posts = postRepository.searchBoardPost(word, board, pageRequest).stream()
                .map(SearchPostList::of)
                .collect(Collectors.toList());
        return SearchPostListResponseDto.of(posts);
    }

    private User getUser() {
        String userEmail = SecurityUtil.getCurrentUserEmail();
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
