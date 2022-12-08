package june.songmelier.service;

import june.songmelier.entity.*;
import june.songmelier.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SongService {

    private final SongRepository songRepository;
    private final FavorRepository favorRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public void createFavor(Long songId, Long memberId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 노래가 존재하지 않습니다."));

        favorRepository.findBySongIdAndMemberId(songId, memberId)
                .ifPresent((x) -> {throw new IllegalArgumentException("이미 좋아요를 누른 곡입니다.");});

        Favor favor = Favor.createFavor(Member.createIdMember(memberId), song);
        favorRepository.save(favor);
    }

    @Transactional
    public void deleteFavor(Long songId, Long memberId) {
        Favor favor = favorRepository.findBySongIdAndMemberId(songId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않은 곡입니다."));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 노래가 존재하지 않습니다."));

        favorRepository.delete(favor);

        song.favorCountDown();
    }

    @Transactional
    public void createBookmark(Long songId, Long memberId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 노래가 존재하지 않습니다."));

        bookmarkRepository.findBySongIdAndMemberId(songId, memberId)
                .ifPresent((x) -> {throw new IllegalArgumentException("이미 싱리스트에 해당하는 곡입니다.");});

        Bookmark bookmark = Bookmark.createBookmark(Member.createIdMember(memberId), song);
        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void deleteBookmark(Long songId, Long memberId) {
        Bookmark bookmark = bookmarkRepository.findBySongIdAndMemberId(songId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("내 싱리스트에 존재하지 않는 곡입니다."));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 노래가 존재하지 않습니다."));

        bookmarkRepository.delete(bookmark);

        song.bookmarkCountDown();
    }

}
