package june.songmelier.controller;

import june.songmelier.dto.SongDto;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.service.MellonChartService;
import june.songmelier.service.TjChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TjChartController {

    private final TjChartService tjChartService;

    @GetMapping("/api/chart/tj")
    public Slice<SongDto.SongChartRes> getSongDetail(@AuthenticationPrincipal PrincipalDetails principal,
                                                     Pageable pageable ) {
        return tjChartService.getTjChartSongs(principal.getMemberId(),pageable);
    }
}
