package june.songmelier.controller;


import june.songmelier.dto.ChartSongDto;
import june.songmelier.entity.Song;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.service.ChartService;
import lombok.RequiredArgsConstructor;
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

//    /**
//     * 멜론차트 100 확인
//     */
//    @GetMapping("/api/chart/mellon")
//    public List<ChartSongDto.melonChartRes> getMelonTop100(@AuthenticationPrincipal PrincipalDetails principal){
//        List<ChartSongDto.melonChartRes> melonChartSongs = chartService.getMelonTop100();
//        return melonChartSongs;
//    }

}
