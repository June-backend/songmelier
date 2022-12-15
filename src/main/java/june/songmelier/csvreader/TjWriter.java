package june.songmelier.csvreader;

import june.songmelier.entity.MellonChart;
import june.songmelier.entity.Song;
import june.songmelier.entity.TjChart;
import june.songmelier.repository.SongRepository;
import june.songmelier.repository.TjChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class TjWriter implements ItemWriter<SongDao> {

    private final SongRepository songRepository;
    private final TjChartRepository tjChartRepository;

    @Override
    public void write(List<? extends SongDao> list) throws Exception {
        List<TjChart> charts = list.stream()
                .map((dao) -> TjChart.createTjChart(dao.getSongId()))
                .collect(Collectors.toList());

        List<Song> readSongs = list.stream()
                .filter((dao) -> songRepository.findByMellonId(dao.getSongId()).isEmpty())
                .map((dao) ->
                        Song.createSong(dao.getTitle(), dao.getSinger(), dao.getImageUrl(), dao.getPublishedDate(), dao.getSongId()))
                .collect(Collectors.toList());

        songRepository.saveAll(readSongs);

        tjChartRepository.deleteAll();
        tjChartRepository.saveAll(charts);
    }
}