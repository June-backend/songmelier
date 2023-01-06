package june.songmelier.utils;

import june.songmelier.dto.SongDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class MellonApi implements SongApi {
    public static final String SEARCH_TITLE_SELECTOR = "#frm_searchSong > div > table > tbody > tr";
    public static final String SEARCH_SINGER_SELECTOR = "#frm_searchArtist > div > table > tbody > tr";
    private static final String MELLON_SEARCH_URL = "https://www.melon.com/search/total/index.htm";
    private static final String MELLON_SEARCH_DETAIL_URL = "https://www.melon.com/song/detail.htm";

    @Override
    public Optional<List<SongDto.SearchApiRes>> searchByTitle(String title) {
        Document doc = getDocument(MELLON_SEARCH_URL, "q", title);
        Elements songElements = getSongElements(doc, SEARCH_TITLE_SELECTOR);

        if (songElements.next().isEmpty()) {
            return Optional.empty();
        }

        return getSearchApiRes(songElements);
    }

    @Override
    public Optional<List<SongDto.SearchApiRes>> searchBySinger(String singer) {
        Document doc = getDocument(MELLON_SEARCH_URL, "q", singer);
        Elements songElements = getSongElements(doc, SEARCH_SINGER_SELECTOR);

        if (songElements.next().isEmpty()) {
            return Optional.empty();
        }

        return getSearchApiRes(songElements);
    }

    private Optional<List<SongDto.SearchApiRes>> getSearchApiRes(Elements songElements) {
        LinkedList<SongDto.SearchApiRes> res = new LinkedList<>();
        for (Element element : songElements) {
            SongPreview songPreview = getSongTitleAndSingerAndId(element);

            Document detailDoc = getDocument(MELLON_SEARCH_DETAIL_URL, "songId", songPreview.getSongId());
            SongDetail songDetail = getSongImgAndPubDate(detailDoc);

            res.add(createSearchApiRes(songPreview, songDetail));
        }
        return Optional.of(res);
    }

    private SongDto.SearchApiRes createSearchApiRes(SongPreview songPreview, SongDetail songDetail) {
        return new SongDto.SearchApiRes(Long.valueOf(songPreview.getSongId()), songPreview.getTitle(), songPreview.getSinger(),
                songDetail.imageUrl, songDetail.pubDate);
    }

    private SongPreview getSongTitleAndSingerAndId(Element element) {
        String title = element.getElementsByClass("fc_gray").text();
        String singer = element.getElementById("artistName").select("span").text();
        String songId = element.getElementsByClass("wrap pd_none left")
                .select("input").attr("value");

        return new SongPreview(title, singer, songId);
    }

    private Elements getSongElements(Document doc, String selector) {
        return doc.select(selector);
    }

    private SongDetail getSongImgAndPubDate(Document detailDoc) {
        String imageUrlSelector = "#downloadfrm > div > div > div.thumb > a > img";
        String pubDateSelector = "#downloadfrm > div > div > div.entry > div.meta > dl > dd:nth-child(4)";

        String imageUrl = detailDoc.select(imageUrlSelector).attr("src");
        String pubDate = detailDoc.select(pubDateSelector).text();
        return new SongDetail(imageUrl, pubDate);
    }

    private Document getDocument(String mellonUrl, String query, String title) {
        try {
            return Jsoup.connect(mellonUrl).headers(getHeader()).data(query, title).get();
        } catch (IOException e) {
            throw new IllegalArgumentException("mellon document get error");
        }
    }

    private Map<String, String> getHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "ko-KR,ko;q=0.9");
        headers.put("Cache-Control", "max-age=0");
        headers.put("Host", "www.melon.com");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
        return headers;
    }

    @Getter
    @AllArgsConstructor
    private static class SongDetail {
        private String imageUrl;
        private String pubDate;
    }

    @Getter
    @AllArgsConstructor
    private static class SongPreview {
        private String title;
        private String singer;
        private String songId;
    }

}
