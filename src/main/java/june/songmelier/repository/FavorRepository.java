package june.songmelier.repository;

import june.songmelier.entity.Favor;
import june.songmelier.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavorRepository extends JpaRepository<Favor, Long> {
    Optional<Favor> findBySongIdAndMemberId(Long songId, Long memberId);
}
