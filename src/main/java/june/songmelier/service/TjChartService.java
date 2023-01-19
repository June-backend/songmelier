package june.songmelier.service;


import june.songmelier.dto.SongDto;
import june.songmelier.repository.BookmarkRepository;
import june.songmelier.repository.MellonChartRepository;
import june.songmelier.repository.TjChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TjChartService {

    private final TjChartRepository tjChartRepository;
    private final BookmarkRepository bookmarkRepository;


    @Transactional
    public Slice<SongDto.SongChartRes> getTjChartSongs(Long memberId, Pageable pageable) {
        Slice<Object[]> songs = tjChartRepository.findall(pageable);
        // SONG_ID ,TITLE, SINGER , IMAGE_URL ,HIGH_DIFFICULT ,LOW_DIFFICULT ,RAP_DIFFICULT ,MOOD  ,ITEM_ID
        Slice<SongDto.SongChartRes> result = songs.map(s -> new SongDto.SongChartRes(
                Long.valueOf((String.valueOf(s[0]))),String.valueOf(s[1]),
                String.valueOf(s[2]),String.valueOf(s[3]),String.valueOf(s[4]),String.valueOf(s[5]),
                String.valueOf(s[6]),String.valueOf(s[7]),
                bookmarkRepository.findBySongIdAndMemberId(Long.valueOf((String.valueOf(s[0]))),memberId).isPresent()
        ));

        return result;

    }

}
