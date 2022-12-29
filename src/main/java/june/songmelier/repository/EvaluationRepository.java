package june.songmelier.repository;

import june.songmelier.entity.Bookmark;
import june.songmelier.entity.Evaluation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    Optional<Evaluation> findBySongIdAndMemberId(Long songId, Long memberId);

//    List<Evaluation> findByMemberId(Long memberId); //Optional.. //page 전

    Slice<Evaluation> findByMemberId(Long memberId, Pageable pageable);
}
