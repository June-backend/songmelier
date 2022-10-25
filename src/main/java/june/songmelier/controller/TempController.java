package june.songmelier.controller;

import june.songmelier.security.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TempController {

    /**
     * 백엔드 내부적인 kakao social login page를 반환하는 임시 controller
     */
    @GetMapping("/kakao")
    public String kakaoLoginPage() {
        return "login.html";
    }

    /**
     * spring security가 정상 작동하고 PrincipalDetails가 잘 들어오는지 확인 하는 테스트 Controller
     *
     * @param principalDetails
     */
    @GetMapping("/api/test")
    @ResponseBody
    public String denied(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        return principalDetails.getUsername() + "  " + principalDetails.getMemberId();
    }
}
