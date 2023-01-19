package june.songmelier.controller;


import june.songmelier.dto.MemberDto;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.security.oauth.Kakao;
import june.songmelier.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final Kakao kakao;
    private final MemberService memberService;

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


    @PostMapping("/login/oauth2/client")
    public MemberDto.ClientSignUpRes clientLogin(@RequestBody Map<String, String> socialId) {
        return memberService.loginByClient(socialId.get("socialId"));
    }
}


