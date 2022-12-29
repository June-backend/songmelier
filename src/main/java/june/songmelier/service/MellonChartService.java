package june.songmelier.service;


import june.songmelier.dto.SongDto;
import june.songmelier.entity.Bookmark;
import june.songmelier.entity.Favor;
import june.songmelier.entity.Song;
import june.songmelier.repository.BookmarkRepository;
import june.songmelier.repository.FavorRepository;
import june.songmelier.repository.MellonChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MellonChartService {

    private final MellonChartRepository mellonChartRepository;
    private final BookmarkRepository bookmarkRepository;

//    @Transactional
//    public List<SongDto.SongMellonChartRes> getMellonChartSongs(Long memberId) {
//        List<Object[]> songs = mellonChartRepository.findall();
//        // SONG_ID ,TITLE, SINGER , IMAGE_URL ,HIGH_DIFFICULT ,LOW_DIFFICULT ,RAP_DIFFICULT ,MOOD  ,MELLON_ID
//        List<SongDto.SongMellonChartRes> result = new ArrayList<SongDto.SongMellonChartRes>();
//        for (Object[] o : songs){
//            Optional<Bookmark> bookmark = bookmarkRepository.findBySongIdAndMemberId(Long.valueOf((String.valueOf(o[0]))),memberId);
//            SongDto.SongMellonChartRes song = new SongDto.SongMellonChartRes(Long.valueOf((String.valueOf(o[0]))),String.valueOf(o[1]),
//                    String.valueOf(o[2]),String.valueOf(o[3]),String.valueOf(o[4]),String.valueOf(o[5]),bookmark.isPresent());
//                    result.add(song);
//        }
//        return result;
//
//    }

    @Transactional
    public Slice<SongDto.SongChartRes> getMellonChartSongs(Long memberId, Pageable pageable) {
        Slice<Object[]> songs = mellonChartRepository.findall(pageable);
        // SONG_ID ,TITLE, SINGER , IMAGE_URL ,HIGH_DIFFICULT ,LOW_DIFFICULT ,RAP_DIFFICULT ,MOOD  ,MELLON_ID
       Slice<SongDto.SongChartRes> result = songs.map(s -> new SongDto.SongChartRes(
               Long.valueOf((String.valueOf(s[0]))),String.valueOf(s[1]),
               String.valueOf(s[2]),String.valueOf(s[3]),String.valueOf(s[4]),String.valueOf(s[5]),
               bookmarkRepository.findBySongIdAndMemberId(Long.valueOf((String.valueOf(s[0]))),memberId).isPresent()
       ));

       return result;

    }

}
