package june.songmelier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EvaluationDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Request {
        private Integer highDifficult;
        private Integer lowDifficult;
        private Integer rapDifficult;
        private Integer mood;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class IdResponse {
        private Long evaluationId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class EvaluationRes{
        private Long evaluationId;
        private Integer highDifficult;
        private Integer lowDifficult;
        private Integer rapDifficult;
        private Integer mood;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class MyEvaluationsRes {

        private EvaluationDto.EvaluationRes evaluation;
        private SongDto.SongEvaluationRes song;
    }





}
