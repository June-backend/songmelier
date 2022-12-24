package june.songmelier.controller;

import june.songmelier.dto.EvaluationDto;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    /**
     * 노래 평가하기 -> return evaluationId
     */
    @PostMapping("/api/song/{songId}/evaluation")
    public EvaluationDto.IdResponse CreateSongEvaluation(@PathVariable("songId") Long songId,
                               @AuthenticationPrincipal PrincipalDetails principalDetails,
                               @RequestBody EvaluationDto.Request evaluationRequest) {

        return evaluationService.songEvaluation(songId, principalDetails.getMemberId(), evaluationRequest);
    }

    /**
     * 노래 평가 삭제하기 -> return evaluationId
     */
    @PostMapping("/api/song/{songId}/evaluation/{evaluationId}/delete")
    public void songEvaluationDelete(@PathVariable("songId") Long songId,
                               @PathVariable("evaluationId") Long evaluationId,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        evaluationService.deleteSongEvaluation(songId, principalDetails.getMemberId(), evaluationId);
    }

    /**
     * 내가 평가한 노래들 보기
     */
    @GetMapping("/api/member/evaluation")
    public List<EvaluationDto.MyEvaluationsRes> GetMyEvaluations(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return evaluationService.getMyEvaluations(principalDetails.getMemberId());
    }

}
