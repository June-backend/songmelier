package june.songmelier.dto;


import june.songmelier.entity.Bookmark;
import june.songmelier.entity.Favor;
import june.songmelier.entity.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    @NoArgsConstructor
    public static class BookmarkRes {
        private Long bookmarkId;
        private SimpleSong song;


        public BookmarkRes(Bookmark bookmark) {
            this.bookmarkId = bookmark.getId();
            this.song = new SimpleSong(bookmark.getSong());
        }
    }


    @Getter
    public static class FavorRes {
        private Long favor_id;
        private SimpleSong song;

        public FavorRes(Favor favor) {
            this.favor_id = favor.getId();
            this.song = new SimpleSong(favor.getSong());
        }
    }

    @Getter
    private static class SimpleSong {
        private Long songId;
        private String title;
        private String singer;
        private String imageUrl;
        private String rapDifficult;
        private String highDifficult;
        private String lowDifficult;
        private String mood;
        
        public SimpleSong(Song song) {
            this.songId = song.getId();
            this.title = song.getTitle();
            this.singer = song.getSinger();
            this.imageUrl = song.getImageUrl();
            this.rapDifficult = song.getRapDifficult();
            this.highDifficult = song.getHighDifficult();
            this.lowDifficult = song.getLowDifficult();
            this.mood = song.getMood();
        }
    }

    //평가탭의 song detail
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class SongEvaluationRes{
        private Long songId;
        private String title;
        private String singer;
        private String imageUrl;
    }

    //멜론차트의 song
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class SongChartRes{
        private Long songId;
        private String title;
        private String singer;
        private String imageUrl;
        private String totalDifficult;
        private String mood;
        private boolean isMySingList;

        public SongChartRes(Song song, boolean isMySingList) {
            this.songId = song.getId();
            this.title = song.getTitle();
            this.singer = song.getSinger();
            this.imageUrl = song.getImageUrl();
            this.mood = song.getMood();
            this.isMySingList = isMySingList;
            this.totalDifficult = song.getHighDifficult();
        }
    }

    @Getter
    static public class SongSearchRes{
        private List<SongChartRes> song = new ArrayList<>();
    }

    //내가 작성한 코멘트 확인 song
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class MyCommentSongRes{

        private Long songId;
        private String title;
        private String singer;
        private String imageUrl;
        private String rapDifficult;
        private String highDifficult;
        private String lowDifficult;
        private String mood;
    }


    @Getter
    static public class SearchApiRes {
        private Long itemId;
        private String title;
        private String singer;
        private String imageUrl;
        private String publishDate;

        public SearchApiRes(Long itemId, String title, String singer, String imageUrl, String publishDate) {
            this.itemId = itemId;
            this.title = title;
            this.singer = singer;
            this.imageUrl = imageUrl;
            this.publishDate = publishDate;
        }
    }

}
