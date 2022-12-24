package june.songmelier.dto;


import june.songmelier.entity.Bookmark;
import june.songmelier.entity.Favor;
import june.songmelier.entity.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.print.Book;

public class SongDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class SongRes {
        private Long songId;
        private String title;
        private String singer;
        private String imageUrl;
        private String publishDate;
        private Long favorCount;
        private Long scrapCount;
        private Long commentCount;
        private String rapDifficult;
        private String highDifficult;
        private String lowDifficult;
        private String mood;
        private boolean isMySinglist;
        private boolean isFavor;


        public SongRes(Song song, boolean isFavor, boolean isMySinglist) {
            this.songId = song.getId();
            this.title = song.getTitle();
            this.singer = song.getSinger();
            this.imageUrl = song.getImageUrl();
            this.publishDate = song.getPublishedDate();
            this.favorCount = song.getFavorCount();
            this.scrapCount = song.getBookmarkCount();
            this.commentCount = song.getCommentCount();
            this.lowDifficult = song.getLowDifficult();
            this.rapDifficult = song.getRapDifficult();
            this.highDifficult = song.getHighDifficult();
            this.mood = song.getMood();
            this.isMySinglist = isMySinglist;
            this.isFavor = isFavor;

        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class SongEvaluationRes{
        private Long songId;
        private String title;
        private String singer;
        private String imageUrl;
    }
}
