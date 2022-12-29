package june.songmelier.service;

import june.songmelier.dto.ChartSongDto;
import june.songmelier.dto.SongDto;
import june.songmelier.entity.Bookmark;
import june.songmelier.entity.Song;
import june.songmelier.repository.BookmarkRepository;
import june.songmelier.repository.ChartRepository;
import june.songmelier.repository.EvaluationRepository;
import june.songmelier.repository.MellonChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChartService {

    private final ChartRepository chartRepository;
    private final BookmarkRepository bookmarkRepository;


    public List<ChartSongDto.randomSongRes> get10RandomSongs() {
        List<Song> randomsongs = chartRepository.get10RandomSongs();
        List<ChartSongDto.randomSongRes> result = randomsongs.stream()
                .map(song->new ChartSongDto.randomSongRes(song.getId(),song.getTitle(), song.getSinger(), song.getImageUrl()))
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public Slice<SongDto.SongChartRes> getSonginSongmelierChart(Long memberId, Pageable pageable) {
        Slice<Object[]> songs = chartRepository.findSonginSongmelierChart(pageable);

        Slice<SongDto.SongChartRes> result = songs.map(s -> new SongDto.SongChartRes(
                Long.valueOf((String.valueOf(s[0]))),String.valueOf(s[1]),
                String.valueOf(s[2]),String.valueOf(s[3]),String.valueOf(s[4]),String.valueOf(s[5]),
                bookmarkRepository.findBySongIdAndMemberId(Long.valueOf((String.valueOf(s[0]))),memberId).isPresent()
        ));
        return result;
    }


}

