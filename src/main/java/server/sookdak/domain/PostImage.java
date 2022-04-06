package server.sookdak.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {
    @Id
    @Column(name = "post_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String url;

    public static PostImage createPostImage(Post post, String url) {
        PostImage postImage = new PostImage();
        postImage.post = post;
        postImage.url = url;
        return postImage;
    }
}
