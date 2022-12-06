package june.songmelier.repository;

import june.songmelier.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findBySongIdAndMemberId(Long songId, Long memberId);
}
