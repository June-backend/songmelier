package june.songmelier.controller;


import june.songmelier.dto.ChartSongDto;
import june.songmelier.dto.SongDto;
import june.songmelier.entity.Song;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    /**
     * 차트에서 랜덤으로 10곡 확인
     */
    @GetMapping("/api/chart/random")
        public List<ChartSongDto.randomSongRes> get10RandomSongs(@AuthenticationPrincipal PrincipalDetails principal){
        List<ChartSongDto.randomSongRes> randomSongs = chartService.get10RandomSongs();
        return randomSongs;
    }


    /**
     * 송믈리에 차트에서 갖고오기
     */
    @GetMapping("/api/chart/songmelier")
    public Slice<SongDto.SongChartRes>  getSonginSongmelierChart(@AuthenticationPrincipal PrincipalDetails principal,
                                                                 Pageable pageable ){
        return chartService.getSonginSongmelierChart(principal.getMemberId(), pageable);
    }


}
