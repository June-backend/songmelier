package june.songmelier.entity;

import june.songmelier.entity.superclass.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Song extends TimeStamped {

    @Id @GeneratedValue
    @Column(name = "song_id")
    private Long id;

    private String title;
    private String singer;
    private String imageUrl;
    private String publishedDate;

    private Long favorCount;
    private Long bookmarkCount;
    private Long commentCount;

    private String highDifficult;
    private String lowDifficult;
    private String rapDifficult;
    private String mood;

    @OneToMany(mappedBy = "song")
    private List<Comment> commentList = new ArrayList<>();

    //연관 관계 설정 필요
    //--------------------------------------------------- 생성자 ----------------------------------------------------------//

    private Song(String title, String singer, String imageUrl, String publishedDate) {
        this.title = title;
        this.singer = singer;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;
        this.favorCount = 0L;
        this.bookmarkCount = 0L;
        this.commentCount = 0L;
        this.highDifficult = "0 0 0 0 0";
        this.lowDifficult = "0 0 0 0 0";
        this.rapDifficult = "0 0 0 0 0";
        this.mood = "0 0 0 0 0";
    }
    //--------------------------------------------------- 생성 편의자 ----------------------------------------------------------//

    public static Song createSong(String title, String singer, String imageUrl, String publishedDate) {
        return new Song(title, singer, imageUrl, publishedDate);
    }

    //--------------------------------------------------- 편의 메서드 ----------------------------------------------------------//
    public void favorCountUp() {
        this.favorCount += 1;
    }

    public void favorCountDown() {
        this.favorCount -= 1;
    }

    public void bookmarkCountUp() {
        this.bookmarkCount += 1;
    }

    public void bookmarkCountDown() {
        this.bookmarkCount -= 1;
    }

    public void commentCountUp() {
        this.commentCount += 1;
    }

    public void commentCountDown() {
        this.commentCount -= 1;
    }

    public void newEvaluation(Long highDifficult, Long lowDifficult, Long rapDifficult, Long mood) {
        String[] highDifficultList = this.highDifficult.split(" ");
        highDifficultList[highDifficult.intValue() - 1] =
                String.valueOf(Integer.parseInt(highDifficultList[highDifficult.intValue() - 1]) + 1);
        this.highDifficult = String.join(" ", highDifficultList);

        String[] lowDifficultList = this.lowDifficult.split(" ");
        lowDifficultList[lowDifficult.intValue() - 1] =
                String.valueOf(Integer.parseInt(lowDifficultList[lowDifficult.intValue() - 1]) + 1);
        this.lowDifficult = String.join(" ", lowDifficultList);

        String[] rapDifficultList = this.rapDifficult.split(" ");
        rapDifficultList[rapDifficult.intValue() - 1] =
                String.valueOf(Integer.parseInt(rapDifficultList[rapDifficult.intValue() - 1]) + 1);
        this.rapDifficult = String.join(" ", rapDifficultList);

        String[] moodList = this.mood.split(" ");
        moodList[mood.intValue() - 1] =
                String.valueOf(Integer.parseInt(moodList[mood.intValue() - 1]) + 1);
        this.mood = String.join(" ", moodList);
    }


}
