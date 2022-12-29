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

    //곡의 코맨트 확인
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class CommentRes {
        private Long commentId;
        private String comment;
        private Long likeCount;
        private LocalDateTime createdAt;
        private boolean isLiked;
        private MemberDto.MemberRes member;
    }

    //내 코맨트 확인 comment dto
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class MyCommentDto {
        private Long commentId;
        private String comment;
    }

    //내 코맨트 확인 api dto
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class MyCommentRes {
        private SongDto.MyCommentSongRes song;
        private CommentDto.MyCommentDto comment;
    }

}
