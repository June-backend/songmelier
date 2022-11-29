package june.songmelier.entity;

import june.songmelier.entity.superclass.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentStatus extends TimeStamped {
    @Id
    @GeneratedValue
    @Column(name = "commentstatus_id")
    private Long id;

    private boolean isLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    //--------------------------------------------------- 생성자 ----------------------------------------------------------//

    private CommentStatus(boolean isLike, Member member, Comment comment) {
        this.isLike = isLike;
        this.member = member;
        this.comment = comment;
    }


    //--------------------------------------------------- 생성 편의자 ----------------------------------------------------------//
    public static CommentStatus createCommentStatus(boolean isLike, Member member, Comment comment) {
        CommentStatus commentStatus = new CommentStatus(isLike, member, comment);
        if (isLike) {
            comment.likeCountUp();
        } else {
            comment.hateCountUp();
        }
        return commentStatus;
    }
}
