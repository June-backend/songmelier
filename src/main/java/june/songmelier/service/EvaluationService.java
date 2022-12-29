package june.songmelier.service;

import june.songmelier.dto.CommentDto;
import june.songmelier.dto.EvaluationDto;
import june.songmelier.dto.MemberDto;
import june.songmelier.dto.SongDto;
import june.songmelier.entity.*;
import june.songmelier.repository.EvaluationRepository;
import june.songmelier.repository.SongRepository;
import june.songmelier.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //내가 평가한 노래들 보기 page전
//    @Transactional
//    public  List<EvaluationDto.MyEvaluationsRes> getMyEvaluations(Long memberId) {
//        List<Evaluation> evaluations = evaluationRepository.findByMemberId(memberId);
//        List<EvaluationDto.MyEvaluationsRes> result = new ArrayList<EvaluationDto.MyEvaluationsRes>();
//
//
//        for(Evaluation evaluation : evaluations){
//            SongDto.SongEvaluationRes evaluationSong = new SongDto.SongEvaluationRes(evaluation.getSong().getId(),evaluation.getSong().getTitle(),evaluation.getSong().getSinger(),evaluation.getSong().getImageUrl());
//            EvaluationDto.EvaluationRes evaluationDetail = new EvaluationDto.EvaluationRes(evaluation.getId(),evaluation.getHighDifficult(),evaluation.getHighDifficult(),evaluation.getRapDifficult(),evaluation.getMood());
//            EvaluationDto.MyEvaluationsRes myEvaluation = new EvaluationDto.MyEvaluationsRes(evaluationDetail, evaluationSong);
//            result.add(myEvaluation);
//        }
//        return result;
//        }

    @Transactional
    public Slice<EvaluationDto.MyEvaluationsRes> getMyEvaluations1(Long memberId, Pageable pageable) {
        Slice<Evaluation> evaluations = evaluationRepository.findByMemberId(memberId,pageable);

        //slice 결과를 slice에 채워넣기
        Slice<EvaluationDto.MyEvaluationsRes> dtoSlice = evaluations.map(e -> new EvaluationDto.MyEvaluationsRes(new EvaluationDto.EvaluationRes(e.getId(),e.getHighDifficult(),e.getLowDifficult(),e.getRapDifficult(),e.getMood()),
                new SongDto.SongEvaluationRes(e.getSong().getId(),e.getSong().getTitle(),e.getSong().getSinger(),e.getSong().getImageUrl())));

        return dtoSlice;
    }

    }

