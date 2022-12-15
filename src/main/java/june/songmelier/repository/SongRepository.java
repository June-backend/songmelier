package june.songmelier.repository;

import june.songmelier.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findByMellonId(Long songId);
}
