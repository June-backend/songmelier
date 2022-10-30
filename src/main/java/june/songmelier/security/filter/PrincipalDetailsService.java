package june.songmelier.security.filter;

import june.songmelier.entity.Member;
import june.songmelier.repository.MemberRepository;
import june.songmelier.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    //UserDetailsService를 쓰지는 않지만, 구현체가 없다면 WebSecurityConfigurerAdapter에서 authenticationManager를 사용 불가능

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("로그인 요청한 회원이 DB에 없다"));

        return new PrincipalDetails(member.getId(), member.getUsername());
    }
}