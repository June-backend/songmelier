package june.songmelier.repository;

import june.songmelier.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    Optional<Evaluation> findBySongIdAndMemberId(Long songId, Long memberId);
}
