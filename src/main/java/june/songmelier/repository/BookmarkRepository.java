package june.songmelier.repository;

import june.songmelier.entity.Bookmark;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findBySongIdAndMemberId(Long songId, Long memberId);

    @Query("select bm from Bookmark bm join fetch bm.song where bm.member.id = :memberId")
    Slice<Bookmark> findByMemberId(@Param("memberId") Long memberId, Pageable pageable);
}
