package june.songmelier.repository;

import june.songmelier.entity.Comment;
import june.songmelier.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.util.List;

public interface ChartRepository  extends JpaRepository<Song, Long> {

   Page<Song> findTop10By(Pageable pageable);

   @Query(value = "select * from song order by random() limit 10",nativeQuery = true)
   List<Song> get10RandomSongs();


   @Query(value = "SELECT SONG_ID ,TITLE, SINGER , IMAGE_URL ,HIGH_DIFFICULT ,LOW_DIFFICULT ,RAP_DIFFICULT ,MOOD  ,MELLON_ID FROM SONG ORDER BY (BOOKMARK_COUNT +FAVOR_COUNT) DESC , CREATED_AT asc", nativeQuery = true)
   Slice<Object[]> findSonginSongmelierChart(Pageable pageable);

}
