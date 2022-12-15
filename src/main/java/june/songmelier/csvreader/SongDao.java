package june.songmelier.csvreader;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class SongDao {

    @Id
    private Long songId;

    private String imageUrl;
    private String publishedDate;
    private String singer;
    private String title;
}
