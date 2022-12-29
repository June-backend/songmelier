package june.songmelier.repository;

import june.songmelier.entity.MellonChart;
import june.songmelier.entity.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface MellonChartRepository extends JpaRepository<MellonChart, Long> {
    @Query(value = "select SONG_ID ,TITLE, SINGER , IMAGE_URL ,HIGH_DIFFICULT ,LOW_DIFFICULT ,RAP_DIFFICULT ,MOOD  ,MELLON_ID  from SONG left join MELLON_CHART on song.MELLON_ID  = MELLON_CHART.MELLON_CHART_ID",nativeQuery = true)
    Slice<Object[]> findall(Pageable pageable);

//    select * from SONG right join MELLON_CHART on song.MELLON_ID  = MELLON_CHART.MELLON_CHART_ID
//    @Query("select m from MellonChart m join Song s")
//    <Song> findal2();//현웅님께 여쭤보기

}
