package june.songmelier.entity;

import june.songmelier.entity.superclass.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Evaluation extends TimeStamped {
    @Id
    @GeneratedValue
    @Column(name = "evaluation_id")
    private Long id;

    private Integer highDifficult;
    private Integer lowDifficult;
    private Integer rapDifficult;
    private Integer mood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "comment_id")
    private Comment comment;

//--------------------------------------------------- 생성자 ----------------------------------------------------------//
    private Evaluation(Integer highDifficult, Integer lowDifficult, Integer rapDifficult, Integer mood, Member member, Song song) {
        this.highDifficult = highDifficult;
        this.lowDifficult = lowDifficult;
        this.rapDifficult = rapDifficult;
        this.mood = mood;
        this.member = member;
        this.song = song;
    }

    //--------------------------------------------------- 생성 편의자 ----------------------------------------------------------//
    public static Evaluation createEvaluation(Integer highDifficult, Integer lowDifficult, Integer rapDifficult, Integer mood, Member member, Song song) {
        Evaluation evaluation = new Evaluation(highDifficult, lowDifficult, rapDifficult, mood, member, song);

        song.newEvaluation(highDifficult, lowDifficult, rapDifficult, mood);

        return evaluation;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
