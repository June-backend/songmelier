package june.songmelier.security.oauth;

import june.songmelier.dto.MemberDto;
import june.songmelier.dto.oauth.KakaoProfile;
import june.songmelier.dto.oauth.OAuthToken;
import june.songmelier.entity.Member;
import june.songmelier.repository.MemberRepository;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Kakao {

    private final MemberRepository memberRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;
    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String authorizationGrantType;

//    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
//    private String authorizationUri;
//    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
//    private String clientSecret;

    @Transactional
    public MemberDto.LoginRes login(String providerName, String code) {

        OAuthToken tokenResponse = getTokenFromKakao(code); //provider에 해당하는 카카오의 OAuthToken 얻어서
        KakaoProfile kakaoProfile = getUserProfileFromKakao(providerName, tokenResponse); //카카오 프로필 요청해서 얻고

        MemberDto.LoginRes response = saveMember(providerName, kakaoProfile);//멤버 저장

        String accessToken = jwtTokenUtils.
                createAccessToken(response.getMember().getMemberId(), response.getMember().getUsername());
        String refreshToken = jwtTokenUtils.createRefreshToken(response.getMember().getMemberId());

        //hardcoding need refactoring
//        redisUtils.setRefreshTokenDataExpire(
//                String.valueOf(response.getMember().getMemberId()),
//                refreshToken, jwtTokenUtils.getRefreshTokenExpireTime(refreshToken)
//        );

        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }



    //==============================================================================================//

    private OAuthToken getTokenFromKakao(String code) {
        OAuthToken oAuthToken = WebClient.create()
                .post()
                .uri(tokenUri)
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(OAuthToken.class)
                .block();

        return oAuthToken;
    }

    private MultiValueMap<String, String> tokenRequest(String code) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", authorizationGrantType);
        formData.add("redirect_uri", redirectUri);
        formData.add("client_id", clientId);
//        formData.add("client_secret", provider.getClientSecret());

        return formData;
    }

    //카카오 프로필 얻기
    private KakaoProfile getUserProfileFromKakao(String providerName, OAuthToken tokenResponse) {

        return WebClient.create()
                .get()
                .uri(userInfoUri)
                .headers(header -> header.setBearerAuth(tokenResponse.getAccess_token()))
                .retrieve()
                .bodyToMono(KakaoProfile.class)
                .block();
    }

    private MemberDto.LoginRes saveMember(String providerName, KakaoProfile kakaoProfile) {
//        String email = kakaoProfile.getKakao_account().getEmail();         //카카오에서 받아온 email을 email으로
        String kakaoId = kakaoProfile.getId() + "_" + providerName;      //카카오에서 받아온 아이디를 kakaoId로.
        String username = kakaoProfile.getProperties().getNickname();      //카카오에서 받아온 nickname을 username으로.
        String imageUrl = kakaoProfile.getProperties().getProfile_image(); //카카오에서 받아온 Profile_image를 imageUrl로.


        //멤버가 db에 없을 경우 db에 멤버값을 저장한다.
        Optional<Member> member = memberRepository.findByEmail(kakaoId);
        boolean isFirst = member.isEmpty();

        if (member.isEmpty()) {
            member = Optional.of(memberRepository.save(Member.createMember(kakaoId,null, username, imageUrl, null)));
        }

        PrincipalDetails principalDetails = new PrincipalDetails(member.get().getId(), username); //principalDetails을 생성해줌
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new MemberDto.LoginRes(new MemberDto.MemberRes(member.get().getId(), username, imageUrl), isFirst);
    }

}
