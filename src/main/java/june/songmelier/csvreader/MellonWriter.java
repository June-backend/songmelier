package june.songmelier.csvreader;

import june.songmelier.entity.MellonChart;
import june.songmelier.entity.Song;
import june.songmelier.repository.MellonChartRepository;
import june.songmelier.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class MellonWriter implements ItemWriter<SongDao> {

    private final SongRepository songRepository;
    private final MellonChartRepository mellonChartRepository;

    @Override
    public void write(List<? extends SongDao> list) throws Exception {
        List<MellonChart> charts = list.stream()
                .map((dao) -> MellonChart.createMellonChart(dao.getSongId()))
                .collect(Collectors.toList());

        List<Song> readSongs = list.stream()
                .filter((dao) -> songRepository.findByItemId(dao.getSongId()).isEmpty())
                .map((dao) ->
                        Song.createSong(dao.getTitle(), dao.getSinger(), dao.getImageUrl(), dao.getPublishedDate(), dao.getSongId()))
                .collect(Collectors.toList());

        songRepository.saveAll(readSongs);

        mellonChartRepository.deleteAll();
        mellonChartRepository.saveAll(charts);
    }
}