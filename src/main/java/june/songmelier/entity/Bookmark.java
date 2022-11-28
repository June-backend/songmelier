package june.songmelier.entity;

import june.songmelier.entity.superclass.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends TimeStamped {
    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    //--------------------------------------------------- 생성자 ----------------------------------------------------------//
    private Bookmark(Member member, Song song) {
        this.member = member;
        this.song = song;
    }


    //--------------------------------------------------- 생성 편의자 ----------------------------------------------------------//
    public static Bookmark createBookmark(Member member, Song song) {
        Bookmark bookmark = new Bookmark(member, song);
        song.bookmarkCountUp();
        return bookmark;
    }


}
