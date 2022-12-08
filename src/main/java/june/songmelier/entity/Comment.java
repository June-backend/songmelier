package june.songmelier.entity;

import june.songmelier.entity.superclass.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String text;
    private Long likeCount;
    private Long hateCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;


    //--------------------------------------------------- 생성자 ----------------------------------------------------------//
    public Comment(String text, Member member, Song song) {
        this.text = text;
        this.member = member;
        this.song = song;

        this.likeCount = 0L;
        this.hateCount = 0L;
    }

    //--------------------------------------------------- 생성 편의자 ----------------------------------------------------------//
    public static Comment createComment(String text, Member member, Song song) {
        Comment comment = new Comment(text, member, song);
        song.commentCountUp();
        return comment;
    }

    public static Comment createCommentByEvaluation(String text, Member member, Song song, Evaluation evaluation) {
        Comment comment = new Comment(text, member, song);
        song.commentCountUp();
        evaluation.setComment(comment);
        return comment;
    }

    //--------------------------------------------------- 편의 메서드 ----------------------------------------------------------//

    public void likeCountUp() {
        this.likeCount += 1;
    }

    public void hateCountUp() {
        this.hateCount += 1;
    }

    public void likeCountDown() {
        this.likeCount -= 1;
    }

    public void hateCountDown() {
        this.hateCount -= 1;
    }
}
