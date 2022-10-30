package june.songmelier.controller;


import june.songmelier.dto.MemberDto;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.security.oauth.Kakao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final Kakao kakao;

    /**
     * Social 로그인을 하여 자체적인 JWT TOKEN 반환
     *
     * @param provider ex) kakao, google
     * @param code     provider 가 제공하는 인증 코드
     */
    @GetMapping("/login/oauth2/{provider}")
    public MemberDto.LoginRes socialLogin(@PathVariable("provider") String provider, @RequestParam String code) {
        return kakao.login(provider, code);
    }
}
