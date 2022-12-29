package june.songmelier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChartSongDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class randomSongRes{
        private Long songId;
        private String title;
        private String singer;
        private String imageUrl;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class melonChartRes{
        private Long songId;
        private String title;
        private String singer;
        private String imageUrl;
//        private Long totalDifficult;
//        private Long mood;
//        private boolean isMySinglist;
        private String totalDifficult;
        private String mood;
        private boolean isMySingList;
    }

}
