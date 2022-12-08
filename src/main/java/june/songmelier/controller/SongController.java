package june.songmelier.controller;


import june.songmelier.dto.CommentDto;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    /**
     * 노래 하트 누르기
     */
    @PostMapping("/api/song/{songId}/favor")
    public void createFavor(
            @PathVariable("songId") Long songId, @AuthenticationPrincipal PrincipalDetails principal) {

        songService.createFavor(songId, principal.getMemberId());
    }

    /**
     * 노래 하트 취소
     */
    @PostMapping("/api/song/{songId}/favor/delete")
    public void deleteFavor(@PathVariable("songId") Long songId, @AuthenticationPrincipal PrincipalDetails principal) {
        songService.deleteFavor(songId, principal.getMemberId());

    }

    /**
     * 싱리스트 추가
     */
    @PostMapping("/api/song/{songId}/singlist")
    public void createSingList(@PathVariable("songId") Long songId, @AuthenticationPrincipal PrincipalDetails principal) {
        songService.createBookmark(songId, principal.getMemberId());

    }

    /**
     * 싱리스트 제거
     */
    @PostMapping("/api/song/{songId}/singlist/delete")
    public void deleteSingList(@PathVariable("songId") Long songId, @AuthenticationPrincipal PrincipalDetails principal) {
        songService.deleteBookmark(songId, principal.getMemberId());
    }



}
