package server.sookdak.dto.res.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.sookdak.domain.PostScrap;

import java.util.List;

@Getter
@NoArgsConstructor
public class ScrapListResponseDto {
    private List<ScrapList> scraps;

    private ScrapListResponseDto(List<ScrapList> scraps) { this.scraps = scraps; }

    public static ScrapListResponseDto of(List<ScrapList> scraps) { return new ScrapListResponseDto(scraps); }

    @Getter
    public static class ScrapList {
        private Long postId;
        private String content;
        private String createdAt;
        private int likes;
        private int comments;
        private boolean image;

        public ScrapList(PostScrap postScrap) {
            this.postId = postScrap.getPost().getPostId();
            this.content = postScrap.getPost().getContent();
            this.createdAt = postScrap.getPost().getCreatedAt();
            this.likes = postScrap.getPost().getLikes().size();
            this.comments = postScrap.getPost().getComments().size();
            if(postScrap.getPost().getImages() != null) {
                this.image = true;
            } else {
                this.image = false;
            }
        }


    }
}
