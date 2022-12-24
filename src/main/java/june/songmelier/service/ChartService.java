package june.songmelier.service;

import june.songmelier.dto.ChartSongDto;
import june.songmelier.entity.Bookmark;
import june.songmelier.entity.Song;
import june.songmelier.repository.BookmarkRepository;
import june.songmelier.repository.ChartRepository;
import june.songmelier.repository.EvaluationRepository;
import june.songmelier.repository.MellonChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final MellonChartRepository mellonChartRepository;

    public List<ChartSongDto.randomSongRes> get10RandomSongs() {
        List<Song> randomsongs = chartRepository.get10RandomSongs();
        List<ChartSongDto.randomSongRes> result = randomsongs.stream()
                .map(song->new ChartSongDto.randomSongRes(song.getId(),song.getTitle(), song.getSinger(), song.getImageUrl()))
                .collect(Collectors.toList());
        return result;
    }

//    public List<ChartSongDto.melonChartRes> getMelonTop100(){
//        List<Song> top100 = mellonChartRepository.findAll();
//
//        List<ChartSongDto.melonChartRes> result = top100.stream()
//                .map(song->new ChartSongDto.melonChartRes(song.getId(),song.getTitle(), song.getSinger(), song.getImageUrl(),song.getHighDifficult(), song.getLowDifficult(), true))
//                .collect(Collectors.toList());
//        return result;
//    }

}

