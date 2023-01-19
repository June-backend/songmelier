package june.songmelier.controller;


import june.songmelier.dto.SongDto;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
     * 나의 선호 노래 확인
     */
    @GetMapping("/api/member/favor")
    public Slice<SongDto.FavorRes> getFavorList(
            @AuthenticationPrincipal PrincipalDetails principal,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return songService.getFavor(principal.getMemberId(), pageable);
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

    /**
     * 나의 싱리스트 확인
     */
    @GetMapping("/api/member/singlist")
    public Slice<SongDto.BookmarkRes> getSingList(
            @AuthenticationPrincipal PrincipalDetails principal,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return songService.getBookmark(principal.getMemberId(), pageable);
    }

    /**
     * 노래 상세 정보 갖고오기
     */
    @GetMapping("/api/song/{songId}/songdetail")
    public SongDto.SongRes getSongDetail(@PathVariable("songId") Long songId, @AuthenticationPrincipal PrincipalDetails principal) {

        return songService.getSongDetail(songId, principal.getMemberId());
    }

    @GetMapping("/api/song/search/title")
    public SongDto.SongSearchRes searchSongByTitle(@RequestParam("q") String title, @AuthenticationPrincipal PrincipalDetails principal) {
        return songService.searchSongByTitle(title, principal.getMemberId());
    }

    @GetMapping("/api/song/search/singer")
    public SongDto.SongSearchRes searchSongBySinger(@RequestParam("q") String singer, @AuthenticationPrincipal PrincipalDetails principal) {
        return songService.searchSongBySinger(singer, principal.getMemberId());
    }
}
