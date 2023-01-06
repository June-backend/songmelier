package june.songmelier.utils;

import june.songmelier.dto.SongDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SongApi {

    public Optional<List<SongDto.SearchApiRes>> searchByTitle(String title);

    public Optional<List<SongDto.SearchApiRes>> searchBySinger(String singer);



}
