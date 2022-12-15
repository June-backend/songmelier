package june.songmelier.service;

import june.songmelier.dto.EvaluationDto;
import june.songmelier.entity.Evaluation;
import june.songmelier.entity.Member;
import june.songmelier.entity.Song;
import june.songmelier.repository.EvaluationRepository;
import june.songmelier.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationService {

    private final SongRepository songRepository;
    private final EvaluationRepository evaluationRepository;

    @Transactional
    public EvaluationDto.IdResponse songEvaluation(Long songId, Long memberId, EvaluationDto.Request evaluationRequest) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 노래입니다."));

        evaluationRepository.findBySongIdAndMemberId(songId, memberId)
                .ifPresent((x) -> {throw new IllegalArgumentException("이미 평가한 곡입니다.");});

        Evaluation evaluation = Evaluation.createEvaluation(evaluationRequest.getHighDifficult(),
                evaluationRequest.getLowDifficult(), evaluationRequest.getRapDifficult(),
                evaluationRequest.getMood(), Member.createIdMember(memberId), song);

        evaluationRepository.save(evaluation);

        //flush 안해도 ID가 나가나 확인
        return new EvaluationDto.IdResponse(evaluation.getId());
    }

    @Transactional
    public void deleteSongEvaluation(Long songId, Long memberId, Long evaluationId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 노래입니다."));

        //exist evaluation? and my evaluation?
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .filter((eval) -> eval.getMember().getId().equals(memberId) && eval.getSong().getId().equals(songId))
                .orElseThrow(() -> new IllegalArgumentException("삭제하기 적절한 Evaluation 이 아닙니다."));

        //modify song evaluation
        song.deleteEvaluation(evaluation.getHighDifficult(), evaluation.getLowDifficult(),
                evaluation.getRapDifficult(), evaluation.getMood());

        //delete evaluation and evaluation comment
        evaluationRepository.delete(evaluation);
    }
}
