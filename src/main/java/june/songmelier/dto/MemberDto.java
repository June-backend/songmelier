package june.songmelier.dto;

import june.songmelier.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {
    @Getter
    @AllArgsConstructor
    public static class LoginRes {
        private String accessToken;
        private String refreshToken;
        private MemberRes member;
        private Boolean isFirst;

        public LoginRes(MemberRes member, Boolean isFirst) {
            this.member = member;
            this.isFirst = isFirst;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class MemberRes {

        private Long memberId;
        private String username;
        private String imageUrl;
    }


    @Getter
    public static class profileRes {
        private String username;
        private String imageUrl;
        private String introduce;

        public profileRes(Member member) {
            this.username = member.getUsername();
            this.imageUrl = member.getImageUrl();
            this.introduce = member.getIntroduce();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Principal {
        private Long memberId;
        private String username;
    }
}
