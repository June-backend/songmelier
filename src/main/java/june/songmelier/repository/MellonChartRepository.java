package june.songmelier.repository;

import june.songmelier.entity.MellonChart;
import june.songmelier.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MellonChartRepository extends JpaRepository<MellonChart, Long> {
//    @Query("select m from melon_chart m order by ")
//    List<Song> findAllOrderByCreatedatAsc();//현웅님께 여쭤보기

}
