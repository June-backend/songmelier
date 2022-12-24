package june.songmelier.controller;

import june.songmelier.dto.SongDto;
import june.songmelier.entity.Song;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.service.MellonChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MellonChartController {

    private final MellonChartService mellonChartService;

    @GetMapping("/api/song/melon/songs")
    public Slice<SongDto.SongMellonChartRes> getSongDetail(@AuthenticationPrincipal PrincipalDetails principal,
                                                          Pageable pageable ) {
        return mellonChartService.getMellonChartSongs(principal.getMemberId(),pageable);
    }

}
