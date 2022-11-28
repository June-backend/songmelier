package june.songmelier.security.oauth;

import june.songmelier.entity.Member;
import june.songmelier.repository.MemberRepository;
import june.songmelier.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();    //google
        String providerId = oAuth2User.getAttribute("sub");                     //google 에서 제공하는 내 고유 ID
        String email = providerId + "_" + provider;  			                          // DB에 저장할 고유한 ID
        String username = oAuth2User.getAttribute("name");
        String imageUrl = oAuth2User.getAttribute("picture");


        Optional<Member> member = memberRepository.findByEmail(email);

        //DB에 없는 사용자라면 회원가입처리
        boolean isFirst = member.isEmpty();
        if (member.isEmpty()) {
            Member newMember = Member.createMember(email, null, username, imageUrl, null);
            Member saveMember = memberRepository.save(newMember);
            member = Optional.of(saveMember);
        }

        return new PrincipalDetails(member.get().getId(), username, isFirst, imageUrl);
    }


}
