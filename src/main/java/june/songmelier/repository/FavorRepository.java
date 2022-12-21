package june.songmelier.repository;

import june.songmelier.entity.Bookmark;
import june.songmelier.entity.Favor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FavorRepository extends JpaRepository<Favor, Long> {
    Optional<Favor> findBySongIdAndMemberId(Long songId, Long memberId);

    @Query("select fav from Favor fav join fetch fav.song where fav.member.id = :memberId")
    Slice<Favor> findByMemberIdWithSong(@Param("memberId") Long memberId, Pageable pageable);
}
