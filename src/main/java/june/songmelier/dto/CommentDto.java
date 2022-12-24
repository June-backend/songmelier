package june.songmelier.dto;

import june.songmelier.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class CreateReq {
        private String text;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class CommentRes {
        private Long commentId;
        private String comment;
        private Long likeCount;
        private LocalDateTime createdAt;
        private boolean isLiked;
        private MemberDto.MemberRes memberRes;
    }

}
