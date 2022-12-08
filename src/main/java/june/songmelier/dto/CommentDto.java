package june.songmelier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static public class CreateReq {
        private String text;
    }
}
