package june.songmelier.entity;

import june.songmelier.entity.superclass.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favor extends TimeStamped {
    @Id
    @GeneratedValue
    @Column(name = "favor_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;


    //--------------------------------------------------- 생성자 ----------------------------------------------------------//
    private Favor(Member member, Song song) {
        this.member = member;
        this.song = song;
    }


    //--------------------------------------------------- 생성 편의자 ----------------------------------------------------------//
    public static Favor createFavor(Member member, Song song) {
        Favor favor = new Favor(member, song);
        song.favorCountUp();
        return favor;
    }


}
