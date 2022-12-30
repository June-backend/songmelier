package june.songmelier.repository;

import june.songmelier.entity.TjChart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TjChartRepository extends JpaRepository<TjChart, Long> {

    @Query(value = "select SONG_ID ,TITLE, SINGER , IMAGE_URL ,HIGH_DIFFICULT ,LOW_DIFFICULT ,RAP_DIFFICULT ,MOOD  ,MELLON_ID  from SONG right join TJ_CHART  on song.MELLON_ID  =TJ_CHART.TJ_CHART_ID order by TJ_CHART.CREATED_AT ",nativeQuery = true)
    Slice<Object[]> findall(Pageable pageable);


}
