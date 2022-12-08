package june.songmelier.repository;

import june.songmelier.entity.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentStatusRepository extends JpaRepository<CommentStatus, Long> {
    Optional<CommentStatus> findByCommentId(Long commentId);
}
