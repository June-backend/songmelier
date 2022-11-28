package june.songmelier.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import june.songmelier.dto.MemberDto;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Component
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        Long memberId = principal.getMemberId();
        String username = principal.getUsername();
        String imageUrl = principal.getImageUrl();

        String accessToken = jwtTokenUtils.createAccessToken(memberId, username);
        String refreshToken = jwtTokenUtils.createRefreshToken(memberId);

        //imageUrl 어떻게 받아올지 생각하기
        MemberDto.LoginRes loginRes = new MemberDto.LoginRes(new MemberDto.MemberRes(memberId, username, imageUrl), principal.isFirstLogin());
        loginRes.setAccessToken(accessToken);
        loginRes.setRefreshToken(refreshToken);

        //응답
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(loginRes);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
